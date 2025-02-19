package com.example.mealmate.ui.Auth.presenter;

import android.content.Context;

import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthCallback;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthHelper;
import com.example.mealmate.ui.Auth.view.login.ILoginView;
import com.example.mealmate.ui.plan.model.PlanMealModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements FirebaseAuthCallback {
    Context context;
    MealsRepository repo;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    SharedPref sharedPref;
    private ILoginView loginView;
    private FirebaseAuthHelper authHelper;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
        this.authHelper = FirebaseAuthHelper.getInstance();
        repo = new MealsRepository(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(context));
        this.sharedPref = SharedPref.getInstance(context.getApplicationContext());// Use the singleton instance
    }


    public boolean validateInputs(String email, String password) {
        boolean isValid = true;
        if (email == null || email.isEmpty()) {
            loginView.showValidationError("Please enter your email.");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.showValidationError("Please enter a valid email address.");
            isValid = false;
        }
        if (password == null || password.isEmpty()) {
            loginView.showValidationError("Please enter your password.");
            isValid = false;
        } else if (password.length() < 6) {
            loginView.showValidationError("password must be at least 6 characters.");
            isValid = false;
        }
        return isValid;
    }

    public void login(String email, String password) {
        if (!validateInputs(email, password)) return;

        loginView.showLoading();
        authHelper.login(email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        sharedPref.setLogged(true);
        sharedPref.setUSERID(firebaseUser.getUid());

        loginView.hideLoading();
        loginView.onLoginSuccess(firebaseUser);
        loadDataFromFirebase();
    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.hideLoading();
        loginView.onLoginFailure(errorMessage);
    }

    void loadDataFromFirebase() {
        repo.getFavMealsFromFireStore(sharedPref.getUSERID())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> {
                            for (MealModel mealModel : list) {
                                repo.insertMealToFavLocal(mealModel)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();

                            }
                            // Do something with the list
                        }

                );

        repo.getPlanMealsFromFireStore(sharedPref.getUSERID())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> {
                            for (PlanMealModel planMealModel : list) {
                                repo.insertMealToPlanLocal(planMealModel)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();

                            }
                            // Do something with the list
                        }

                );


        // Load data from Firebase
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        onSuccess(user);

                    } else {
                        onFailure("Authentication Failed!");
                    }
                });
    }

}

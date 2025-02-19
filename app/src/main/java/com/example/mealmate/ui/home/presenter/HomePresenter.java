package com.example.mealmate.ui.home.presenter;

import android.content.Context;

import com.example.mealmate.R;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthHelper;
import com.example.mealmate.ui.home.view.IHomeView;
import com.example.mealmate.repo.MealsRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    MealsRepository mealsRepository;
    IHomeView view;
    Context context;

    FirebaseAuthHelper authHelper;
    SharedPref sharedPref;

    public HomePresenter(MealsRepository mealsRepository, IHomeView view,Context context) {
        this.mealsRepository = mealsRepository;
        this.view = view;
        this .context = context;
        authHelper = FirebaseAuthHelper.getInstance();
        sharedPref=SharedPref.getInstance(context.getApplicationContext());
    }

    public void getRandomMeal() {
        mealsRepository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModel -> {
                            view.showData(mealModel);
                        },
                        error -> view.showError(error.getMessage())
                );
    }

    public void logout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("264734462193-sulg8qi7l15fepo8j1hn4h3gli339jiq.apps.googleusercontent.com") // Get Web Client ID from Firebase
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(FirebaseApp.getInstance().getApplicationContext(), gso);
        googleSignInClient.signOut();

       authHelper.logout();
      sharedPref.setLogged(false);
        view.onLogout();

    }
}

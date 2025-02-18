package com.example.mealmate.ui.Auth.presenter;

import android.content.Context;

import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthCallback;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthHelper;
import com.example.mealmate.ui.Auth.view.login.ILoginView;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements FirebaseAuthCallback {
    Context context;

    private ILoginView loginView;
    private FirebaseAuthHelper authHelper;

    SharedPref sharedPref;

    public LoginPresenter(ILoginView loginView, Context context) {
       this. context = context;
        this.loginView = loginView;
        this.authHelper = FirebaseAuthHelper.getInstance();
        this.sharedPref=SharedPref.getInstance(context.getApplicationContext());// Use the singleton instance
    }


    public boolean validateInputs(String email, String password) {
        boolean isValid=true;
        if (email == null || email.isEmpty()) {
            loginView.showValidationError("Please enter your email.");
            isValid= false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.showValidationError("Please enter a valid email address.");
            isValid= false;
        }
        if (password == null || password.isEmpty()) {
            loginView.showValidationError("Please enter your password.");
            isValid= false;
        }else if (password.length() < 6) {
            loginView.showValidationError("password must be at least 6 characters.");
            isValid= false;
        }
        return isValid;
    }

    public void login(String email, String password) {
        if (!validateInputs(email, password)) return;

        loginView.showLoading();
        authHelper.login(email, password,this);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        sharedPref.setLogged(true);
        sharedPref.setUSERID(firebaseUser.getUid());

        loginView.hideLoading();
        loginView.onLoginSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.hideLoading();
        loginView.onLoginFailure(errorMessage);
    }
}

package com.example.mealmate.Auth.presenter;

import com.example.mealmate.Auth.firebaseAuth.FirebaseAuthCallback;
import com.example.mealmate.Auth.firebaseAuth.FirebaseAuthHelper;
import com.example.mealmate.Auth.view.login.ILoginView;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements FirebaseAuthCallback {

    private ILoginView loginView;
    private FirebaseAuthHelper authHelper;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.authHelper = FirebaseAuthHelper.getInstance(); // Use the singleton instance
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

        loginView.hideLoading();
        loginView.onLoginSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.hideLoading();
        loginView.onLoginFailure(errorMessage);
    }
}

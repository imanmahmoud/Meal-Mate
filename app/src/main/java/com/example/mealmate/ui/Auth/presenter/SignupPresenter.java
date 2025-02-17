package com.example.mealmate.ui.Auth.presenter;

import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthCallback;
import com.example.mealmate.ui.Auth.firebaseAuth.FirebaseAuthHelper;
import com.example.mealmate.ui.Auth.view.signup.ISignupView;
import com.google.firebase.auth.FirebaseUser;

public class SignupPresenter implements FirebaseAuthCallback {
    private ISignupView signupView;
    private FirebaseAuthHelper authHelper;

    public SignupPresenter(ISignupView signupView) {
        this.signupView = signupView;
        this.authHelper = FirebaseAuthHelper.getInstance(); // Use the singleton instance
    }


    public boolean validateInputs(String name,String email, String password) {
        boolean isValid=true;

        if (name.trim().isEmpty()) {
            signupView.showValidationError("Full name is required!");
            isValid = false;
        }
        if (email == null || email.isEmpty()) {
            signupView.showValidationError("Please enter your email.");
            isValid= false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupView.showValidationError("Please enter a valid email address.");
            isValid= false;
        }
        if (password == null || password.isEmpty()) {
            signupView.showValidationError("Please enter your password.");
            isValid= false;
        }else if (password.length() < 6) {
            signupView.showValidationError("password must be at least 6 characters.");
            isValid= false;
        }
        return isValid;
    }

    public void signup(String name,String email, String password) {
        if (!validateInputs(name,email, password)) return;

        signupView.showLoading();
        authHelper.signup(email, password,this);



    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {

        signupView.hideLoading();
        signupView.onSignupSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String errorMessage) {
        signupView.hideLoading();
        signupView.onSignupFailure(errorMessage);
    }
}

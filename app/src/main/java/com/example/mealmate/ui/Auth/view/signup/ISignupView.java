package com.example.mealmate.ui.Auth.view.signup;

public interface ISignupView {
    void showLoading();
    void hideLoading();
    void onSignupSuccess(Object result);
    void onSignupFailure(String errorMessage);
    void showValidationError(String message); // Add this
}

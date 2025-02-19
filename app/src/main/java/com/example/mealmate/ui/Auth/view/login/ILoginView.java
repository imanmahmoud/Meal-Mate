package com.example.mealmate.ui.Auth.view.login;

public interface ILoginView {
    void showLoading();
    void hideLoading();
    void onLoginSuccess(Object result);
    void onLoginFailure(String errorMessage);
    void showValidationError(String message); // Add this
}

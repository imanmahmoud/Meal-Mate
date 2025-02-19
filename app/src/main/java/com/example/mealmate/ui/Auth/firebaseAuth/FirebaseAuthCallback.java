package com.example.mealmate.ui.Auth.firebaseAuth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public interface FirebaseAuthCallback {
    void onSuccess(FirebaseUser firebaseUser);
    void onFailure(String errorMessage);
}

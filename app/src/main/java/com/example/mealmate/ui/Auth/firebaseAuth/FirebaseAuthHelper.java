package com.example.mealmate.ui.Auth.firebaseAuth;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthHelper {


    private static FirebaseAuthHelper instance;
    private FirebaseAuth firebaseAuth;


    private FirebaseAuthHelper() {
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public static synchronized FirebaseAuthHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthHelper();
        }
        return instance;
    }

    public void login(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }


    public void signup(String email, String password, FirebaseAuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}


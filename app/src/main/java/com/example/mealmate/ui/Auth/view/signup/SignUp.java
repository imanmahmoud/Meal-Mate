package com.example.mealmate.ui.Auth.view.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mealmate.MainActivity;
import com.example.mealmate.ui.Auth.presenter.SignupPresenter;
import com.example.mealmate.R;
import com.example.mealmate.utils.CustomeSnakeBar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class SignUp extends Fragment implements ISignupView {
    Button btnSignup;
    TextView tvSignIn;
    TextInputEditText etName;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    ProgressDialog progressDialog;
    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    SignupPresenter presenter;


    public SignUp() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        presenter = new SignupPresenter(this, getActivity());
        progressDialog = new ProgressDialog(getContext());

        nameInputLayout = view.findViewById(R.id.nameInputLayout);
        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        tvSignIn = view.findViewById(R.id.tvSignIn);
        btnSignup = view.findViewById(R.id.btnSignup);


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordInputLayout.setError(null); // Clear error
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        tvSignIn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signUp_to_login);
        });


        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            presenter.signup(name,email,password);


        });

        return view;
    }


    @Override
    public void showLoading() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false); // Prevent dismissing by tapping outside
        progressDialog.show();


        // new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();

    }

    @Override
    public void onSignupSuccess(Object result) {
        CustomeSnakeBar.showCustomSnackbar(getView(), "Success", getActivity());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

        // Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
       /* progressDialog.setMessage("Success");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);*/
        //go to home screen

    }

    @Override
    public void onSignupFailure(String errorMessage) {

        CustomeSnakeBar.showCustomSnackbar(getView(), errorMessage, getActivity());
        // Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
      /*  progressDialog.setMessage("failed");
        progressDialog.setCancelable(false); // Prevent dismissing by tapping outside
        progressDialog.show();


        new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);*/
    }

    @Override
    public void showValidationError(String message) {
        if (message.contains("name")) {
            nameInputLayout.setError(message);
            nameInputLayout.requestFocus();
        } else if (message.contains("email")) {
            emailInputLayout.setError(message);
            emailInputLayout.requestFocus();
        } else if (message.contains("password")) {
            passwordInputLayout.setError(message);
            passwordInputLayout.requestFocus();
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }
}
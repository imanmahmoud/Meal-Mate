package com.example.mealmate.ui.Auth.view.login;

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

import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.Auth.firebaseAuth.GoogleAuthService;
import com.example.mealmate.ui.Auth.presenter.LoginPresenter;
import com.example.mealmate.MainActivity;
import com.example.mealmate.R;
import com.example.mealmate.utils.CustomeSnakeBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class Login extends Fragment implements ILoginView {

    TextView tvRegister;
    Button loginButton;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    ProgressDialog progressDialog;
    private LoginPresenter presenter;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private Button btnSkip;

    GoogleAuthService googleAuthService;

    Button btnGoogleSignIn;




    public Login() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        presenter = new LoginPresenter(this,getActivity());
        progressDialog = new ProgressDialog(getContext());

        googleAuthService=new GoogleAuthService(requireContext());

        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        loginButton = view.findViewById(R.id.btnLogin);
        btnSkip=view.findViewById(R.id.btn_skip);
        btnGoogleSignIn=view.findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setOnClickListener(v -> {
           googleAuthService.signIn(this);
        });


        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        btnSkip.setOnClickListener(v -> {
            SharedPref.getInstance(getActivity()).setLogged(false);
            SharedPref.getInstance(getActivity()).setUSERID(null);
           Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
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


        // Set click listener for login button
        loginButton.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            presenter.login(email, password);
        });


        tvRegister = view.findViewById(R.id.tvCreateAccount);
        tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_login_to_signUp);
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
    public void onLoginSuccess(Object result) {

      //  CustomeSnakeBar.showCustomSnackbar(getView(), "Login Success", getActivity());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
       /* progressDialog.setMessage("Success");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);
*/
        //go to home screen
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        // CustomeSnakeBar.showCustomSnackbar(getView(), errorMessage, getActivity());

        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
       /* progressDialog.setMessage("failed");
        progressDialog.setCancelable(false); // Prevent dismissing by tapping outside
        progressDialog.show();


        new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);*/
    }

    @Override
    public void showValidationError(String message) {
        if (message.contains("email")) {
            emailInputLayout.setError(message);
            emailInputLayout.requestFocus();
        } else if (message.contains("password")) {
            passwordInputLayout.setError(message);
            passwordInputLayout.requestFocus();
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getActivity(), "Google Sign-In failed!", Toast.LENGTH_SHORT).show();
            }
}
}
}
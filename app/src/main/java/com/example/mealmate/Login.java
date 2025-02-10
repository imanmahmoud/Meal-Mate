package com.example.mealmate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Login extends Fragment {

    TextView tvRegister;



    public Login() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_login, container, false);
        tvRegister=view.findViewById(R.id.tvCreateAccount);
        tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_login_to_signUp);
        });
       return view;
    }
}
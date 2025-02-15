package com.example.mealmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the BottomNavigationView
         bottomNavigationView = findViewById(R.id.bottom_nav);
         manager=getSupportFragmentManager();
        NavHostFragment navHostFragment=(NavHostFragment) manager.findFragmentById(R.id.main_nav_host_fragment);

        // Find the NavController
        NavController navController = navHostFragment.getNavController();

        // Link NavController to BottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
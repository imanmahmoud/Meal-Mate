package com.example.mealmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mealmate.repo.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager manager;

    SharedPref sharedPref=SharedPref.getInstance(this);

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if((item.getItemId()==R.id.favoriteFragment||item.getItemId()==R.id.planFragment)&&sharedPref.getUSERID()==null){
                    Toast.makeText(MainActivity.this,"This feature is not available in guest mode", Toast.LENGTH_LONG).show();
                    return false ;
                }

                return NavigationUI.onNavDestinationSelected(item,navController);

            }
});




    }








}
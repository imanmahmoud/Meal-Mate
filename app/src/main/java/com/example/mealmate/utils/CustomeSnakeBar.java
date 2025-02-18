package com.example.mealmate.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.mealmate.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomeSnakeBar {

    public static void showCustomSnackbar(View view, String message, Context context) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

        // Get the Snackbar's view
        View snackbarView = snackbar.getView();

        // Set background color with rounded corners
        GradientDrawable background = new GradientDrawable();
        background.setColor(context.getResources().getColor(R.color.cream)); // Your custom background color
        background.setCornerRadius(40f); // Adjust this for roundness

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            snackbarView.setBackground(background);
        } else {
            snackbarView.setBackgroundColor(context.getResources().getColor(R.color.cream)); // Fallback for old devices
        }

        // Get the parent layout and modify its parameters
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).width = ViewGroup.LayoutParams.WRAP_CONTENT;  // Wrap content width
            ((FrameLayout.LayoutParams) params).gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; // Bottom center
            ((FrameLayout.LayoutParams) params).bottomMargin = 100; // Adjust margin from the bottom
        }

        snackbarView.setLayoutParams(params);

        // Change text color
        snackbar.setTextColor(Color.BLACK);

        // Change action button color
        snackbar.setActionTextColor(Color.YELLOW);

        snackbar.show();
    }
}

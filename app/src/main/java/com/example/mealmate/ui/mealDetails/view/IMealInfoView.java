package com.example.mealmate.ui.mealDetails.view;

import com.example.mealmate.model.meal.MealModel;

public interface IMealInfoView {

    void showData(MealModel mealModel,String embeddedVideoUrl);
    void showError(String message);
}

package com.example.mealmate.mealDetails.view;

import com.example.mealmate.model.MealModel;

public interface IMealInfoView {

    void showData(MealModel mealModel,String embeddedVideoUrl);
    void showError(String message);
}

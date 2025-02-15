package com.example.mealmate.home.view;

import com.example.mealmate.model.MealModel;

public interface IHomeView {
    void showData(MealModel mealModel);
    void showError(String message);
}

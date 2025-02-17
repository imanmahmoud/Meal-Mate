package com.example.mealmate.ui.home.view;

import com.example.mealmate.model.meal.MealModel;

public interface IHomeView {
    void showData(MealModel mealModel);
    void showError(String message);
}

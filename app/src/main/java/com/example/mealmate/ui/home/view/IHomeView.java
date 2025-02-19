package com.example.mealmate.ui.home.view;

import com.example.mealmate.model.meal.MealModel;

import java.util.List;

public interface IHomeView {
    void showData(MealModel mealModel);

    void showMeals(List<MealModel> mealList);

    void showError(String message);

    void onLogout();
}

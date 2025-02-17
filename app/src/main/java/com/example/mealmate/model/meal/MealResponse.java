package com.example.mealmate.model.meal;

import com.example.mealmate.model.meal.MealModel;

import java.util.List;

public class MealResponse {

    private List<MealModel> meals;

    public List<MealModel> getMeals() {
        return meals;
    }
    public void setMeals(List<MealModel> meals) {
        this.meals = meals;
    }

}

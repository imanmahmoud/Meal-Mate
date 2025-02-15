package com.example.mealmate.model;

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

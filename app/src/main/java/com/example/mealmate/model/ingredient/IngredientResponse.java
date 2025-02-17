package com.example.mealmate.model.ingredient;

import java.util.List;

public class IngredientResponse {
    private List<IngredientModel> meals;

    public List<IngredientModel> getMeals() {
        return meals;
    }
    public void setMeals(List<IngredientModel> meals) {
        this.meals = meals;
    }
}

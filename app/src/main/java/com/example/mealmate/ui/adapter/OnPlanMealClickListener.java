package com.example.mealmate.ui.adapter;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.ui.plan.model.PlanMealModel;

public interface OnPlanMealClickListener {

    void onMealClick(PlanMealModel planMealModel);
    void onFavMinusClick(PlanMealModel planMealModel);
}

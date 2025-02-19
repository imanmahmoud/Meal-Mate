package com.example.mealmate.ui.plan.view;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.ui.plan.model.PlanMealModel;

import java.util.List;

public interface IPlanFragmentView {

    void showData(List<PlanMealModel> planMealModels);
    void ShowMsg(String msg);

}

package com.example.mealmate.ui.search.sectionSearch.view;

import com.example.mealmate.model.meal.MealModel;

import java.util.List;

public interface ISectionSearchView {
    void showMeals(List<MealModel> mealList);
    void showErrMsg(String errorMessage);
}

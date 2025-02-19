package com.example.mealmate.ui.favorite.view;

import com.example.mealmate.model.meal.MealModel;

import java.util.List;

public interface IFavoriteView {
    void showData(List<MealModel> mealModelList);
    void ShowMsg(String Message);
}

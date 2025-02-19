package com.example.mealmate.ui.search.mainSearch.view;

import com.example.mealmate.model.Area.AreaModel;
import com.example.mealmate.model.categories.CategoryModel;
import com.example.mealmate.model.ingredient.IngredientModel;


import java.util.List;

public interface ISearchView {
   public void showCategoryList(List<CategoryModel> categoryModelList);
   public void showIngredientsList(List<IngredientModel> ingredientModelList);
   public void showAreaList(List<AreaModel> areaModelListList);
    public void showErrMsg(String errorMessage);

}

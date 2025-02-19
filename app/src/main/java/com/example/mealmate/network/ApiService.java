package com.example.mealmate.network;

import com.example.mealmate.model.Area.AreaResponse;
import com.example.mealmate.model.categories.CategoryResponse;
import com.example.mealmate.model.ingredient.IngredientResponse;
import com.example.mealmate.model.meal.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String id);

    @GET("categories.php")
     Single<CategoryResponse> getCategories();

    @GET("list.php?i=list")
     Single<IngredientResponse> getIngredients();

    @GET("list.php?a=list")
     Single<AreaResponse> getAreas();

    @GET("filter.php")
     Single<MealResponse> getMealsByCategory(@Query("c") String categoryName);

    @GET("filter.php")
     Single<MealResponse> getMealsByIngredient(@Query("i") String ingredientName);

    @GET("filter.php")
     Single<MealResponse> getMealsByArea(@Query("a") String areaName);


}

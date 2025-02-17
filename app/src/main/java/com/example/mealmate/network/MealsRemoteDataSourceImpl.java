package com.example.mealmate.network;

import com.example.mealmate.model.meal.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealsRemoteDataSourceImpl mealsRemoteDataSource = null;
    ApiService apiService;

    private MealsRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static MealsRemoteDataSourceImpl getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealsRemoteDataSourceImpl();
        }
        return mealsRemoteDataSource;
    }

    public Single<MealResponse> getRandomMeal() {
        return apiService.getRandomMeal();
    }

    public Single<MealResponse> getMealById(String id) {
        return apiService.getMealById(id);
    }

    public Single<MealResponse> getMealsByCategory(String categoryName) {
        return apiService.getMealsByCategory(categoryName);
    }

    public Single<MealResponse> getMealsByIngredient(String ingredientName) {
        return apiService.getMealsByIngredient(ingredientName);
    }

    public Single<MealResponse> getMealsByArea(String areaName) {
        return apiService.getMealsByArea(areaName);
    }


}

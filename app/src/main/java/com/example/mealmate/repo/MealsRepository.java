package com.example.mealmate.repo;

import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.meal.MealResponse;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import io.reactivex.rxjava3.core.Single;

public class MealsRepository {
    MealsRemoteDataSourceImpl remoteSource;
    MealsLocalDataSourceImpl localDataSource;


    public MealsRepository(MealsRemoteDataSourceImpl remoteSource, MealsLocalDataSourceImpl LocalDataSource) {
        this.remoteSource = remoteSource;
        this.localDataSource = LocalDataSource;
    }
    public Single<MealResponse> getRandomMeal() {
        return remoteSource.getRandomMeal();
    }

    public Single<MealResponse> getMealById(String id) {
        return remoteSource.getMealById(id);
    }
    public Single<MealResponse> getMealsByCategory(String categoryName) {
        return remoteSource.getMealsByCategory(categoryName);
    }
    public Single<MealResponse> getMealsByIngredient(String ingredientName) {
        return remoteSource.getMealsByIngredient(ingredientName);
    }
    public Single<MealResponse> getMealsByArea(String areaName) {
        return remoteSource.getMealsByArea(areaName);
    }




}

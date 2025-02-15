package com.example.mealmate.repo;

import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.MealResponse;
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




}

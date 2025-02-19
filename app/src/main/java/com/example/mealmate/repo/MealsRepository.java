package com.example.mealmate.repo;

import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.Area.AreaResponse;
import com.example.mealmate.model.categories.CategoryResponse;
import com.example.mealmate.model.ingredient.IngredientResponse;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.model.meal.MealResponse;
import com.example.mealmate.network.FirebaseHelper;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.ui.plan.model.PlanMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepository {
    MealsRemoteDataSourceImpl remoteSource;
    MealsLocalDataSourceImpl localDataSource;

    FirebaseHelper firebaseHelper ;


    public MealsRepository(MealsRemoteDataSourceImpl remoteSource, MealsLocalDataSourceImpl LocalDataSource) {
        this.remoteSource = remoteSource;
        this.localDataSource = LocalDataSource;
        firebaseHelper =  new FirebaseHelper();
    }
    public Single<MealResponse> getRandomMeal() {
        return remoteSource.getRandomMeal();
    }

    public Single<MealResponse> getMealById(String id) {
        return remoteSource.getMealById(id);
    }

    public Single<CategoryResponse> getCategories() {
        return remoteSource.getCategories();
    }
    public Single<IngredientResponse> getIngredient() {
        return remoteSource.getIngredients();
    }
    public Single<AreaResponse> getAreas() {
        return remoteSource.getAreas();
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

    public Observable<List<MealModel>> getFavoriteMeals(String uid) {
        return localDataSource.getFavoriteMeals(uid);
    }
    public Completable insertMealToFav(MealModel mealModel) {
        firebaseHelper.addMealToFireStore(mealModel);
        return localDataSource.insertMealToFav(mealModel);
    }
    public Completable insertMealToFavLocal(MealModel mealModel) {
        return localDataSource.insertMealToFav(mealModel);
    }
    public Completable deleteMealFromFav(MealModel mealModel) {
        firebaseHelper.deleteMeal(mealModel);
        return localDataSource.deleteMealFromFav(mealModel);
    }
    public Observable<List<PlanMealModel>> getAllplanMeals(String uId, String datee) {
        return localDataSource.getAllplanMeals(uId, datee);
    }
    public Completable insertMealToPlan(PlanMealModel mealModel) {
        firebaseHelper.addMealToPlanFireStore(mealModel);
        return localDataSource.insertMealToPlan(mealModel);
    }
    public Completable insertMealToPlanLocal(PlanMealModel mealModel) {
        return localDataSource.insertMealToPlan(mealModel);
    }
    public Completable deleteMealFromPlan(PlanMealModel mealModel) {
        firebaseHelper.deleteMealFromPlan(mealModel);
        return localDataSource.deleteMealFromPlan(mealModel);
    }

    public Observable<List<PlanMealModel>> getPlanMealsFromFireStore(String uid) {
        return firebaseHelper.getAllPlansMeals(uid);
    }

    public Observable<List<MealModel>> getFavMealsFromFireStore(String uid) {
        return firebaseHelper.getAllMeals(uid);
    }







}

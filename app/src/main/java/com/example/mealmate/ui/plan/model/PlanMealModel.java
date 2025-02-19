package com.example.mealmate.ui.plan.model;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.mealmate.model.meal.MealModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "meal_plan_table",primaryKeys = {"uId","mealId","date"})
public class PlanMealModel implements Serializable  {
    @NonNull
    public MealModel mealModel;
    @NonNull
    public String uId;
    @NonNull
    public String date;
    @NonNull
    public  String mealId;

    public  PlanMealModel(){}

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public PlanMealModel(@NonNull MealModel mealModel, @NonNull String uId, @NonNull String date, @NonNull String mealId) {
        this.mealModel = mealModel;
        this.uId = uId;
        this.date = date;
        this.mealId = mealId;
    }

    @NonNull
    public MealModel getMealModel() {
        return mealModel;
    }

    public void setMealModel(@NonNull MealModel mealModel) {
        this.mealModel = mealModel;
    }

    @NonNull
    public String getuId() {
        return uId;
    }

    public void setuId(@NonNull String uId) {
        this.uId = uId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date=date;
}
}


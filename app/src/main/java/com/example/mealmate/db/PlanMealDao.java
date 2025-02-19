package com.example.mealmate.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.mealmate.ui.plan.model.PlanMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlanMealDao {
    @Query("SELECT * FROM meal_plan_table WHERE uId = :uId and date=:datee")
    Observable<List<PlanMealModel>> getAllplanMeals(String uId, String datee);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertMealToPlan(PlanMealModel mealModel);
    @Delete
    public Completable deleteMealFromPlan(PlanMealModel mealModel);
}

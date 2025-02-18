package com.example.mealmate.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealmate.model.meal.MealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface FavoriteMealDao {
    @Query("SELECT * FROM fav_meals_table WHERE uid = :uid")
    Observable<List<MealModel>> getAllFavMeals(String uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     Completable insertMealToFav(MealModel mealModel);

    @Delete
     Completable deleteMealFromFav(MealModel mealModel);
}

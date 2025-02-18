package com.example.mealmate.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealmate.model.meal.MealModel;


@Database(entities = {MealModel.class}, version = 2/*,exportSchema = false*/)
public abstract class MyDataBase extends RoomDatabase {
    public static MyDataBase instance=null;
    public abstract FavoriteMealDao getFavMealDao();
    public static MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "mealDb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

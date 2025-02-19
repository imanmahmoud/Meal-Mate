package com.example.mealmate.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.ui.plan.model.PlanMealModel;


@Database(entities = {MealModel.class, PlanMealModel.class}, version = 3/*,exportSchema = false*/)
@TypeConverters({Converters.class})
public abstract class MyDataBase extends RoomDatabase {
    public static MyDataBase instance=null;
    public abstract FavoriteMealDao getFavMealDao();
    public abstract PlanMealDao getPlanMealDao();
    public static MyDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "mealDb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

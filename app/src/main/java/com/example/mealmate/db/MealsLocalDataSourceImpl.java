package com.example.mealmate.db;

import android.content.Context;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl {
   private Context context;
   private FavoriteMealDao favoriteMealDao;

    private static MealsLocalDataSourceImpl localDataSource = null;
    private MealsLocalDataSourceImpl(Context _context) {
        this.context = _context;
        MyDataBase db = MyDataBase.getInstance(context.getApplicationContext());
        favoriteMealDao = db.getFavMealDao();
    }

    public static MealsLocalDataSourceImpl getInstance(Context _context) {
        if (localDataSource == null) {
            localDataSource = new MealsLocalDataSourceImpl(_context);
        }
        return localDataSource;
    }

    public Observable<List<MealModel>> getFavoriteMeals(String uid) {
        return favoriteMealDao.getAllFavMeals(uid);
    }
    public Completable insertMealToFav(MealModel mealModel) {
        return favoriteMealDao.insertMealToFav(mealModel);
    }

    public Completable deleteMealFromFav(MealModel mealModel) {
        return favoriteMealDao.deleteMealFromFav(mealModel);
    }


}

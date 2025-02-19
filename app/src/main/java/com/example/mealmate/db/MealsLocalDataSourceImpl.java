package com.example.mealmate.db;

import android.content.Context;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.ui.plan.model.PlanMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl {
   private Context context;
   private FavoriteMealDao favoriteMealDao;
   private PlanMealDao planMealDao;

    private static MealsLocalDataSourceImpl localDataSource = null;
    private MealsLocalDataSourceImpl(Context _context) {
        this.context = _context;
        MyDataBase db = MyDataBase.getInstance(context.getApplicationContext());
        favoriteMealDao = db.getFavMealDao();
        planMealDao = db.getPlanMealDao();
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

    public Observable<List<PlanMealModel>> getAllplanMeals(String uId, String datee) {
      return planMealDao.getAllplanMeals(uId, datee);
    }
    public Completable insertMealToPlan(PlanMealModel mealModel) {
        return planMealDao.insertMealToPlan(mealModel);
    }
    public Completable deleteMealFromPlan(PlanMealModel mealModel) {
        return planMealDao.deleteMealFromPlan(mealModel);
    }


}

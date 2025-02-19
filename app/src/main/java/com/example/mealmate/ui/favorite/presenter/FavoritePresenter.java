package com.example.mealmate.ui.favorite.presenter;

import android.content.Context;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.favorite.view.IFavoriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter {
    private MealsRepository repo;
    private IFavoriteView view;
    Context context;

    SharedPref sharedPref;

    public FavoritePresenter(MealsRepository repo, IFavoriteView view, Context context) {
        this.view = view;
        this.repo = repo;
        this.context = context;
        sharedPref =  SharedPref.getInstance(context.getApplicationContext());
    }

    public void getFavoriteMeals() {
        repo.getFavoriteMeals(sharedPref.getUSERID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> view.showData(list),
                        throwable -> view.ShowMsg(throwable.getMessage())
                );

    }

    public void deleteMealFromFav(MealModel mealId) {
        repo.deleteMealFromFav(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {view.ShowMsg("Deleted Successfully");},
                        throwable -> view.ShowMsg(throwable.getMessage())
                );
    }


}

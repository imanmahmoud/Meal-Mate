package com.example.mealmate.ui.home.presenter;

import com.example.mealmate.ui.home.view.IHomeView;
import com.example.mealmate.repo.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    MealsRepository mealsRepository;
    IHomeView view;

    public HomePresenter(MealsRepository mealsRepository, IHomeView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    public void getRandomMeal() {
        mealsRepository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModel -> {
                            view.showData(mealModel);
                        },
                        error -> view.showError(error.getMessage())
                );
    }
}

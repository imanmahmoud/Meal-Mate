package com.example.mealmate.ui.search.sectionSearch.presenter;

import com.example.mealmate.model.SearchItem;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.search.sectionSearch.view.ISectionSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SectionSearchPresenter {
    MealsRepository repo;
    ISectionSearchView view;
    List<MealModel> mealsBackup = new ArrayList<>();


    public SectionSearchPresenter(MealsRepository mealsRepository, ISectionSearchView view) {
        this.repo = mealsRepository;
        this.view = view;
    }

    public void getAllMeals(SearchItem searchItem) {
        switch (searchItem.getSectionName()) {
            case "Category":
                getMealsByCategory(searchItem.getItemName());
                break;
            case "Area":
                getMealsByArea(searchItem.getItemName());
                break;
            case "Ingredient":
                getMealsByIngredient(searchItem.getItemName());
                break;
        }


    }


    public void getMealsByCategory(String categoryName) {
        repo.getMealsByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            mealsBackup = list;
                            view.showMeals(list);},
                        throwable -> view.showErrMsg(throwable.getMessage())
                );
    }

    public void getMealsByArea(String areaName) {
            repo.getMealsByArea(areaName)
                    .subscribeOn(Schedulers.io())
                    .map(mealResponse -> mealResponse.getMeals())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            list ->  {
                                mealsBackup = list;
                                view.showMeals(list);},
                            throwable -> view.showErrMsg(throwable.getMessage())
                    );
    }

    public void getMealsByIngredient(String ingredientName) {
        repo.getMealsByIngredient(ingredientName)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list ->  {
                            mealsBackup = list;
                            view.showMeals(list);},
                        throwable -> view.showErrMsg(throwable.getMessage())
                );
    }

    public void searchByMeal(String text) {
        if (text == null || text.trim().isEmpty()) {
            view.showMeals(mealsBackup);
        } else {
            List<MealModel> filteredList = mealsBackup.stream().filter(
                    meal -> meal.getStrMeal().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            view.showMeals(filteredList);
        }
    }


}

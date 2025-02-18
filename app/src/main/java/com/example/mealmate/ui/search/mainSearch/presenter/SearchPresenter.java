package com.example.mealmate.ui.search.mainSearch.presenter;

import com.example.mealmate.model.Area.AreaModel;
import com.example.mealmate.model.categories.CategoryModel;
import com.example.mealmate.model.ingredient.IngredientModel;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.search.mainSearch.view.ISearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {

    MealsRepository repo;
    ISearchView view;
    List<CategoryModel> backupCategoryList = new ArrayList<>();
    List<IngredientModel> backupIngredientList = new ArrayList<>();
    List<AreaModel> backupAreaList = new ArrayList<>();


    public SearchPresenter(MealsRepository mealsRepository, ISearchView view) {
        this.repo = mealsRepository;
        this.view = view;
    }

    public void getCategories() {
        repo.getCategories()
                .subscribeOn(Schedulers.io())
                .map(categoryResponse -> categoryResponse.getCategories())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            backupCategoryList= list;
                            view.showCategoryList(list);
                        },
                        throwable -> view.showErrMsg(throwable.getMessage())
                );
    }

    public void getIngredients() {
        repo.getIngredient()
                .subscribeOn(Schedulers.io())
                .map(ingredientResponse -> ingredientResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            backupIngredientList= list;
                            view.showIngredientsList(list);
                        },
                        throwable -> view.showErrMsg(throwable.getMessage())
                );
    }

    public void getAreas() {
        repo.getAreas()
                .subscribeOn(Schedulers.io())
                .map(areaResponse -> areaResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            backupAreaList= list;
                            view.showAreaList(list);
                        },
                        throwable -> view.showErrMsg(throwable.getMessage())
                );

    }


    public void searchBySection(String sectionName, String text) {
        if(sectionName!=null && text!=null) {
            switch (sectionName) {
                case "Category":
                    filterCategoryList(text);
                    break;
                case "Ingredient":
                    filterIngredientList(text);
                    break;
                case "Area":
                    filterAreaList(text);
                    break;
            }
        }
    }

    public void filterCategoryList(String text) {
        if (text == null || text.trim().isEmpty()) {
            view.showCategoryList(backupCategoryList);
        } else {
            List<CategoryModel> filteredList = backupCategoryList.stream().filter(
                    category -> category.getStrCategory().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            view.showCategoryList(filteredList);
        }
    }

    public void filterIngredientList(String text) {
        if (text == null || text.trim().isEmpty()) {
            view.showIngredientsList(backupIngredientList);
        } else {
            List<IngredientModel> filteredList = backupIngredientList.stream().filter(
                    ingredient -> ingredient.getStrIngredient().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            view.showIngredientsList(filteredList);
        }
    }

    public void filterAreaList(String text) {
        if (text == null || text.trim().isEmpty()) {
            view.showAreaList(backupAreaList);
        } else {
            List<AreaModel> filteredList = backupAreaList.stream().filter(
                    area -> area.getStrArea().toLowerCase().startsWith(text.toLowerCase().trim())
            ).collect(Collectors.toList());
            view.showAreaList(filteredList);
        }
    }




}







package com.example.mealmate.ui.mealDetails.presenter;

import android.content.Context;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.mealDetails.view.IMealInfoView;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.plan.model.PlanMealModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealInfoPresenter {

    private IMealInfoView view;
    private MealsRepository repo;
    Context context;

    SharedPref sharedPref;

    public MealInfoPresenter(MealsRepository repo, IMealInfoView view, Context context) {
        this.view = view;
        this.repo = repo;
        this.context = context;
        sharedPref = SharedPref.getInstance(context);
    }

    public void getMealInfo(String mealId) {
        repo.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealModel -> {

                            String embeddedVideo = getVideoId(mealModel.getStrYoutube());
                            view.showData(mealModel, embeddedVideo);
                        },
                        error -> view.showMessage(error.getMessage())
                );

    }

    public String getVideoId(String videoUrl) {
        String video = "";
        if (videoUrl != null && videoUrl.contains("v=")) {
            String videoId = videoUrl.substring(videoUrl.indexOf(videoUrl.substring(videoUrl.indexOf("v=") + 2)));
            video = "<iframe width=\"570\" height=\"300\" src=\"https://www.youtube.com/embed/" + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        }
        return video;
    }

    public void addToFavorite(MealModel meal) {
        if (sharedPref.isLogged()) {
            if (!meal.isFav) {
                repo.insertMealToFav(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    view.showMessage("Added to favorite");
                                    meal.setFav(true);
                                },
                                error -> view.showMessage(error.getMessage()));
            } else {
                view.showMessage("Already in favorite");
            }
        } else {
            view.showMessage("This feature is not available in guest mode");
        }


    }

    public void addToPlan(PlanMealModel meal) {

        repo.insertMealToPlan(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.showMessage("Added to plan"),
                        error -> view.showMessage(error.getMessage()));

    }


}

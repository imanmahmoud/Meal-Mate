package com.example.mealmate.ui.mealDetails.presenter;

import com.example.mealmate.ui.mealDetails.view.IMealInfoView;
import com.example.mealmate.repo.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealInfoPresenter {

    private IMealInfoView view;
    private MealsRepository repo;

    public MealInfoPresenter(MealsRepository repo, IMealInfoView view) {
        this.view = view;
        this.repo = repo;
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
                        error -> view.showError(error.getMessage())
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


}

package com.example.mealmate.ui.plan.presenter;

import android.content.Context;

import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.plan.model.PlanMealModel;
import com.example.mealmate.ui.plan.view.IPlanFragmentView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenter {

    MealsRepository repo;
    IPlanFragmentView view;

    Context context;
    SharedPref sharedPref;

    public PlanPresenter(MealsRepository repo, IPlanFragmentView view, Context context) {
        this.view = view;
        this.repo = repo;
        this.context = context;
        sharedPref = SharedPref.getInstance(context.getApplicationContext());
    }

    public List<String> getCurrentWeek() {

        List<String> week = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));
        for (int i = 0; i < 7; i++) {
            String dayName = sdf.format(calendar.getTime());
            week.add(dayName);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return week;
    }

    public void getMealsByDate(String date) {
        repo.getAllplanMeals(sharedPref.getUSERID(), date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planMealModels -> {
                    view.showData(planMealModels);
                }, throwable -> {
                    view.ShowMsg(throwable.getMessage());
                });
    }

    public void deleteMealFromPlan(PlanMealModel planMealModel) {
        repo.deleteMealFromPlan(planMealModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.ShowMsg("Meal deleted successfully");
                }, throwable -> {
                    view.ShowMsg(throwable.getMessage());
                });
    }



}

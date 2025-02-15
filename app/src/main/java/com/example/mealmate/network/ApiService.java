package com.example.mealmate.network;

import com.example.mealmate.model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();


}

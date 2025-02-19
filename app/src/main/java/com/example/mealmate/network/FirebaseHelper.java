package com.example.mealmate.network;
import android.util.Log;

import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.ui.plan.model.PlanMealModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseHelper {

    private FirebaseFirestore db;
    public FirebaseHelper(){

        db=FirebaseFirestore.getInstance();
    }
    public void addMealToFireStore(MealModel mealModel){
        db.collection("meals").document(mealModel.getuId()+mealModel.getIdMeal()).set(mealModel).addOnSuccessListener(
                v->{
                    Log.i("TAG", "addMealToFireStore:added ");
                }
        ).addOnFailureListener(e->{
            Log.i("TAG", "addMealToFireStore:added ");
        });
    }
    public Observable<List<MealModel>> getAllMeals(String uid) {
        return Observable.<List<MealModel>>create(emitter -> {
            db.collection("meals")
                    .whereEqualTo("uId", uid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealModel> meals = new ArrayList<>();
                        for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                            MealModel meal = d.toObject(MealModel.class);
                            if (meal != null) {
                                meals.add(meal);
                            }
                        }
                        emitter.onNext(meals); // Emit the result
                    })
                    .addOnFailureListener(emitter::onError); // Emit an error if query fails
        }).subscribeOn(Schedulers.io()); // Run on a background thread
    }
    public void deleteMeal(MealModel meal) {
        db.collection("meals")
                .whereEqualTo("idMeal", meal.getIdMeal())
                .whereEqualTo("uId", meal.getuId())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            document.getReference().delete();
                        }
                    }
                });
    }
    //
    public void addMealToPlanFireStore(PlanMealModel mealModel){
        db.collection("plan_meals").document(mealModel.getMealId()+mealModel.getDate()+mealModel.getuId()).set(mealModel).addOnSuccessListener(
                v->{
                    Log.i("TAG", "addMealToFireStore:added ");
                }
        ).addOnFailureListener(e->{
            Log.i("TAG", "addMealToFireStore:added ");
        });
    }
    public Observable<List<PlanMealModel>> getAllPlansMeals(String uid) {
        return Observable.<List<PlanMealModel>>create(emitter -> {
            db.collection("plan_meals")
                    .whereEqualTo("uId", uid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlanMealModel> meals = new ArrayList<>();
                        for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                            PlanMealModel meal = d.toObject(PlanMealModel.class);
                            if (meal != null) {
                                meals.add(meal);
                            }
                        }
                        emitter.onNext(meals); // Emit the result
                    })
                    .addOnFailureListener(emitter::onError); // Emit an error if query fails
        }).subscribeOn(Schedulers.io()); // Run on a background thread
    }
    public void deleteMealFromPlan(PlanMealModel meal) {
        db.collection("plan_meals")
                .whereEqualTo("mealId", meal.getMealId())
                .whereEqualTo("uId", meal.getuId()).
                whereEqualTo("date",meal.getDate())
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            document.getReference().delete();
                        }
                    }
           });
}

}

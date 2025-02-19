package com.example.mealmate.ui.mealDetails.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.adapter.ItemAdapter;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.ui.mealDetails.presenter.MealInfoPresenter;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.plan.model.PlanMealModel;
import com.example.mealmate.utils.CustomeSnakeBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MealInfoFragment extends Fragment implements IMealInfoView {
    MealInfoPresenter presenter;
    MealModel meal;

    ImageView ivRecipeImage;
    TextView tvRecipeName;
    TextView tvRecipeCategory;
    TextView tvRecipeArea;
    TextView tvRecipeInstructions;
    WebView webView;
    ImageButton ibFavorite;
    ImageButton ibPlan;

    RecyclerView recyclerView;
    private ItemAdapter ingredientsAdapter;
    PlanMealModel planMeal=new PlanMealModel();




    public MealInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_info, container, false);
        presenter = new MealInfoPresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(),  MealsLocalDataSourceImpl.getInstance(getActivity())), this);
      //  meal=MealInfoFragmentArgs.fromBundle(getArguments()).getMealObject();
        String mealId = MealInfoFragmentArgs.fromBundle(getArguments()).getMealObject().getIdMeal();

        //close at network error
        presenter.getMealInfo(mealId);

        ibFavorite = view.findViewById(R.id.favorite_icon);
        ibPlan = view.findViewById(R.id.calendar_icon);
        ibPlan.setOnClickListener(v -> showDatePicker());

        recyclerView = view.findViewById(R.id.ingredients_recycler);
        // GridLayoutManager layoutManager=new GridLayoutManager();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        ingredientsAdapter = new ItemAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(ingredientsAdapter);

        ivRecipeImage = view.findViewById(R.id.recipe_image);
        tvRecipeName = view.findViewById(R.id.recipe_title);
        tvRecipeCategory = view.findViewById(R.id.category);
        tvRecipeArea = view.findViewById(R.id.origin);
        tvRecipeInstructions = view.findViewById(R.id.instructions_text);
        webView = view.findViewById(R.id.youtube_webview);


        ibFavorite.setOnClickListener(v -> {
            meal.uId= SharedPref.getInstance(getActivity()).getUSERID();

            presenter.addToFavorite(meal);
        });

        return view;
    }


    @Override
    public void showData(MealModel mealModel, String embeddedVideoUrl) {
        meal = mealModel;
      // CustomeSnakeBar.showCustomSnackbar(recyclerView, "Success", getActivity());
        planMeal.mealModel = meal;
        planMeal.mealId=meal.getIdMeal();
        planMeal.uId=SharedPref.getInstance(getActivity()).getUSERID();



        Glide.with(getActivity())
                .load(meal.getStrMealThumb())//.circleCrop()
               /* .apply(new RequestOptions().override(100, 100))*/
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivRecipeImage);
        ingredientsAdapter.setList(meal.getAllIngredients());
        loadVideo(embeddedVideoUrl);
        tvRecipeName.setText(meal.getStrMeal());
        tvRecipeCategory.setText(meal.getStrCategory());
        tvRecipeArea.setText(meal.getStrArea());
        tvRecipeInstructions.setText(meal.getStrInstructions());
    }

    @Override
    public void showMessage(String message) {
        CustomeSnakeBar.showCustomSnackbar(recyclerView, message, getActivity());
    }

    public void loadVideo(String videoId) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(videoId, "text/html", "utf-8");
    }


    private  void showDatePicker(){
            Calendar calendar = Calendar.getInstance();

            // Get the start of the current week (Monday)
            calendar.set(Calendar.DAY_OF_WEEK,calendar.get(Calendar.DAY_OF_WEEK));
            long startOfWeek = calendar.getTimeInMillis();

            // Get the end of the current week (Sunday)
            calendar.add(Calendar.DAY_OF_WEEK, 6);
            long endOfWeek = calendar.getTimeInMillis();

            // Reset to today’s date for default selection
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
                        String selectedDate = sdf.format(selectedCalendar.getTime());

                        planMeal.setDate(selectedDate);
                        presenter.addToPlan(planMeal);
                    },
                    year, month, day
            );


            // Set min and max dates (restrict to the current week)
            datePickerDialog.getDatePicker().setMinDate(startOfWeek);
            datePickerDialog.getDatePicker().setMaxDate(endOfWeek);
            datePickerDialog.show();}

   }





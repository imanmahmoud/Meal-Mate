package com.example.mealmate.ui.mealDetails.view;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.ui.adapter.ItemAdapter;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.ui.mealDetails.presenter.MealInfoPresenter;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MealInfoFragment extends Fragment implements IMealInfoView {
    MealInfoPresenter presenter;
    MealModel meal;

    ImageView ivRecipeImage;
    TextView tvRecipeName;
    TextView tvRecipeCategory;
    TextView tvRecipeArea;
    TextView tvRecipeInstructions;
    WebView webView;

    RecyclerView recyclerView;
    private ItemAdapter ingredientsAdapter;


    public MealInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealInfoPresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(), new MealsLocalDataSourceImpl()), this);
        String mealId = MealInfoFragmentArgs.fromBundle(getArguments()).getMealId();
        presenter.getMealInfo(mealId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_info, container, false);

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

        return view;
    }


    @Override
    public void showData(MealModel mealModel, String embeddedVideoUrl) {
        showCustomSnackbar(recyclerView, "Success", getActivity());


        meal = mealModel;
        Glide.with(getActivity())
                .load(meal.getStrMealThumb())//.circleCrop()
                .apply(new RequestOptions().override(100, 100))
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
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(recyclerView, "Failed", Snackbar.LENGTH_LONG);

// Change text color
        snackbar.setTextColor(getResources().getColor(R.color.black));

// Change background color
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.cream));

        snackbar.show();

    }

    public void loadVideo(String videoId) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(videoId, "text/html", "utf-8");
    }


    public static void showCustomSnackbar(View view, String message, Context context) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

        // Get the Snackbar's view
        View snackbarView = snackbar.getView();

        // Set background color with rounded corners
        GradientDrawable background = new GradientDrawable();
        background.setColor(context.getResources().getColor(R.color.cream)); // Your custom background color
        background.setCornerRadius(40f); // Adjust this for roundness

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            snackbarView.setBackground(background);
        } else {
            snackbarView.setBackgroundColor(context.getResources().getColor(R.color.cream)); // Fallback for old devices
        }

        // Get the parent layout and modify its parameters
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        if (params instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) params).width = ViewGroup.LayoutParams.WRAP_CONTENT;  // Wrap content width
            ((FrameLayout.LayoutParams) params).gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; // Bottom center
            ((FrameLayout.LayoutParams) params).bottomMargin = 100; // Adjust margin from the bottom
        }

        snackbarView.setLayoutParams(params);

        // Change text color
        snackbar.setTextColor(Color.BLACK);

        // Change action button color
        snackbar.setActionTextColor(Color.YELLOW);

        snackbar.show();
    }

}
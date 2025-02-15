package com.example.mealmate.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.home.presenter.HomePresenter;
import com.example.mealmate.model.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;


public class HomeFragment extends Fragment implements IHomeView {

    ImageView mealImage;
    TextView mealName;
    TextView mealDescription;
    CardView cvMeal;
    HomePresenter presenter;

    MealModel meal;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change the constructor of HomePresenter to the following MealsLocalDataSourceImpl() to fix the bug
        presenter = new HomePresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(), new MealsLocalDataSourceImpl()), this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mealImage = view.findViewById(R.id.iv_random_meal);
        mealName = view.findViewById(R.id.tv_meal_title);
        mealDescription = view.findViewById(R.id.tv_meal_description);
        cvMeal = view.findViewById(R.id.card_random_meal);



        return view;

    }

    @Override
    public void showData(MealModel mealModel) {
        meal = mealModel;
        Glide.with(getActivity())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions().override(100, 100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealDescription.setText(meal.getStrInstructions());

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
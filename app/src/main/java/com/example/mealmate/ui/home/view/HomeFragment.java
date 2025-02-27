package com.example.mealmate.ui.home.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.AuthActivity;
import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.repo.SharedPref;
import com.example.mealmate.ui.adapter.MealAdapter;
import com.example.mealmate.ui.adapter.OnMealClickListener;
import com.example.mealmate.ui.home.presenter.HomePresenter;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.utils.CustomeSnakeBar;
import com.example.mealmate.utils.networkConnection.NetworkResponse;
import com.example.mealmate.utils.networkConnection.NetworkUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements IHomeView, NetworkResponse, OnMealClickListener {

    ImageView mealImage;
    TextView mealName;
    TextView mealDescription;
    CardView cvMeal;
    HomePresenter presenter;
    RecyclerView recyclerView;

    TextView ivLogout;

    MealModel meal;
    View view;
    Group group;
    MealAdapter myAdapter;
    LottieAnimationView lottieAnimationView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change the constructor of HomePresenter to the following MealsLocalDataSourceImpl() to fix the bug


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        ivLogout = view.findViewById(R.id.ic_logout);
        group = view.findViewById(R.id.main_group);
        lottieAnimationView = view.findViewById(R.id.lottie_animation);

        recyclerView = view.findViewById(R.id.rv_meals);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        myAdapter = new MealAdapter(getActivity(), new ArrayList<>(), this, false);
        recyclerView.setAdapter(myAdapter);

        mealImage = view.findViewById(R.id.iv_random_meal);
        mealName = view.findViewById(R.id.tv_meal_title);
        mealDescription = view.findViewById(R.id.tv_meal_description);
        cvMeal = view.findViewById(R.id.card_random_meal);

        presenter = new HomePresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getActivity())), this, getActivity());



        if(NetworkUtils.isInternetAvailable(getActivity())) {
            onNetworkConncted();
            NetworkUtils.registerNetworkCallback(getActivity(), this);
        }else {
            onNetworkDisconnected();
            NetworkUtils.registerNetworkCallback(getActivity(), this);
        }


        cvMeal.setOnClickListener(v -> {
            // Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_mealInfoFragment);

            HomeFragmentDirections.ActionHomeFragment2ToMealInfoFragment action = HomeFragmentDirections.actionHomeFragment2ToMealInfoFragment(meal);
            Navigation.findNavController(v).navigate(action);
        });

        if (SharedPref.getInstance(requireContext()).isLogged()) {
            ivLogout.setText("Logout");
        } else {
            ivLogout.setText("Login");
        }


        ivLogout.setOnClickListener(v -> {
            if (SharedPref.getInstance(requireContext()).isLogged()) {
                showLogoutDialog();
            } else {
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            /* presenter.logout();*/
        });

        return view;

    }

    @Override
    public void showData(MealModel mealModel) {
        meal = mealModel;
        Glide.with(getActivity())
                .load(meal.getStrMealThumb())
                .into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealDescription.setText(meal.getStrInstructions());

    }

    @Override
    public void showMeals(List<MealModel> mealList) {
        myAdapter.setList(mealList);
    }

    @Override
    public void showError(String message) {
        CustomeSnakeBar.showCustomSnackbar(recyclerView, message, getActivity());
    }

    @Override
    public void onLogout() {

        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onNetworkConncted() {
        group.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.GONE);
        presenter.getRandomMeal();
        presenter.getMealsByArea();
    }

    @Override
    public void onNetworkDisconnected() {
        group.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.logout())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onMealClick(MealModel mealModel) {
        HomeFragmentDirections.ActionHomeFragment2ToMealInfoFragment action = HomeFragmentDirections.actionHomeFragment2ToMealInfoFragment(meal);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onFavMinusClick(MealModel mealModel) {
        //not needed
    }
}
package com.example.mealmate.ui.plan.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.adapter.MealAdapter;
import com.example.mealmate.ui.adapter.OnMealClickListener;
import com.example.mealmate.ui.adapter.OnPlanMealClickListener;
import com.example.mealmate.ui.adapter.PlanMealAdapter;
import com.example.mealmate.ui.favorite.presenter.FavoritePresenter;
import com.example.mealmate.ui.favorite.view.FavoriteFragmentDirections;
import com.example.mealmate.ui.plan.model.PlanMealModel;
import com.example.mealmate.ui.plan.presenter.PlanPresenter;
import com.example.mealmate.ui.plan.view.dateAdapter.DateAdapter;
import com.example.mealmate.ui.plan.view.dateAdapter.OnDateCardClickListener;
import com.example.mealmate.utils.CustomeSnakeBar;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements IPlanFragmentView, OnDateCardClickListener, OnPlanMealClickListener {

    RecyclerView dateRecyclerView;
    RecyclerView mealRecyclerView;
    PlanPresenter presenter;

    DateAdapter dateAdapter;
    PlanMealAdapter myAdapter;
    View view;



    public PlanFragment() {
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
         view= inflater.inflate(R.layout.fragment_plan, container, false);

        presenter = new PlanPresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(),  MealsLocalDataSourceImpl.getInstance(getActivity())), this, getActivity());

        mealRecyclerView = view.findViewById(R.id.rv_plan_meals);

        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mealRecyclerView.setHasFixedSize(true);

        myAdapter = new PlanMealAdapter(getActivity(), new ArrayList<>(), this,true);
        mealRecyclerView.setAdapter(myAdapter);



        dateRecyclerView = view.findViewById(R.id.rv_dates);

        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        dateRecyclerView.setHasFixedSize(true);

        dateAdapter = new DateAdapter(getActivity(), new ArrayList<>(), this);
        dateRecyclerView.setAdapter(dateAdapter);
       dateAdapter.setList(presenter.getCurrentWeek());


        return view;
    }

    @Override
    public void onDateCardClick(String date) {
        presenter.getMealsByDate(date);
    }


    @Override
    public void showData(List<PlanMealModel> planMealModels) {
        myAdapter.setList(planMealModels);
    }

    @Override
    public void ShowMsg(String msg) {
        CustomeSnakeBar.showCustomSnackbar(mealRecyclerView, msg, getActivity());
    }

    @Override
    public void onMealClick(PlanMealModel planMealModel) {
        Navigation.findNavController(view).navigate(PlanFragmentDirections.actionPlanFragmentToMealInfoFragment(planMealModel.mealModel));
    }

    @Override
    public void onFavMinusClick(PlanMealModel planMealModel) {
        presenter.deleteMealFromPlan(planMealModel);
    }
}
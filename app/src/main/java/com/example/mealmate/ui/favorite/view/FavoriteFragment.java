package com.example.mealmate.ui.favorite.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.adapter.MealAdapter;
import com.example.mealmate.ui.adapter.OnMealClickListener;
import com.example.mealmate.ui.favorite.presenter.FavoritePresenter;
import com.example.mealmate.ui.search.sectionSearch.view.SectionSearchFragmentDirections;
import com.example.mealmate.utils.CustomeSnakeBar;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements IFavoriteView, OnMealClickListener {

    RecyclerView recyclerView;
    MealAdapter myAdapter;
    FavoritePresenter presenter;
    View view;






    public FavoriteFragment() {
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
        view= inflater.inflate(R.layout.fragment_favorite, container, false);
        presenter = new FavoritePresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(),  MealsLocalDataSourceImpl.getInstance(getActivity())), this, getActivity());
        presenter.getFavoriteMeals();
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        myAdapter = new MealAdapter(getActivity(), new ArrayList<>(), this,true);
        recyclerView.setAdapter(myAdapter);



        return view;
    }

    @Override
    public void showData(List<MealModel> mealModelList) {
        myAdapter.setList(mealModelList);
    }

    @Override
    public void ShowMsg(String message) {
        //CustomeSnakeBar.showCustomSnackbar(view, Message,getActivity());
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(MealModel mealModel) {
        Navigation.findNavController(view).navigate(FavoriteFragmentDirections.actionFavoriteFragmentToMealInfoFragment(mealModel));
    }

    @Override
    public void onFavMinusClick(MealModel mealModel) {
        presenter.deleteMealFromFav(mealModel);
    }
}
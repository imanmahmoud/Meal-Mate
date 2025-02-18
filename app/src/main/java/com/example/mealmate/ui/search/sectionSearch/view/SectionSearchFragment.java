package com.example.mealmate.ui.search.sectionSearch.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.SearchItem;
import com.example.mealmate.model.meal.MealModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.adapter.MealAdapter;
import com.example.mealmate.ui.adapter.OnMealClickListener;
import com.example.mealmate.ui.search.mainSearch.presenter.SearchPresenter;
import com.example.mealmate.ui.search.mainSearch.view.SearchFragmentDirections;
import com.example.mealmate.ui.search.sectionSearch.presenter.SectionSearchPresenter;

import java.util.ArrayList;
import java.util.List;


public class SectionSearchFragment extends Fragment implements ISectionSearchView , OnMealClickListener {


    RecyclerView recyclerView;
    MealAdapter myAdapter;
    SectionSearchPresenter presenter;
    EditText etSearch;
    TextView tvTitle;
    View view;
    SearchItem searchItem;
    //ImageView ivFavoriteMinusIcon;

    public SectionSearchFragment() {
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
         view= inflater.inflate(R.layout.fragment_section_search, container, false);
         searchItem= SectionSearchFragmentArgs.fromBundle(getArguments()).getSearchItem();
        presenter = new SectionSearchPresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(),  MealsLocalDataSourceImpl.getInstance(getActivity())), this);
         presenter.getAllMeals(searchItem);
         tvTitle=view.findViewById(R.id.sectionTitle);
         tvTitle.setText(searchItem.getItemName() +"'s Meals");
         //ivFavoriteMinusIcon=view.findViewById(R.id.img_fav_minus_icon);
        // ivFavoriteMinusIcon.setVisibility(View.GONE);

         recyclerView = view.findViewById(R.id.mealsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        myAdapter = new MealAdapter(getActivity(), new ArrayList<>(), this,false);
        recyclerView.setAdapter(myAdapter);
        etSearch = view.findViewById(R.id.searchEditText);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.searchByMeal(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onMealClick(MealModel mealModel) {
        Navigation.findNavController(view).navigate(SectionSearchFragmentDirections.actionSectionSearchFragmentToMealInfoFragment(mealModel));

    }

    @Override
    public void onFavMinusClick(MealModel mealModel) {
        //not needed
    }

    @Override
    public void showMeals(List<MealModel> mealList) {
        myAdapter.setList(mealList);
    }

    @Override
    public void showErrMsg(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
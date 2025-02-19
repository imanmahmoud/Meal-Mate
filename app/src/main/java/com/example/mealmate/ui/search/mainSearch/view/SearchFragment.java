package com.example.mealmate.ui.search.mainSearch.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.Area.AreaModel;
import com.example.mealmate.model.SearchItem;
import com.example.mealmate.model.categories.CategoryModel;
import com.example.mealmate.model.ingredient.IngredientModel;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.repo.MealsRepository;
import com.example.mealmate.ui.home.view.HomeFragmentDirections;
import com.example.mealmate.ui.search.mainSearch.presenter.SearchPresenter;
import com.example.mealmate.ui.search.mainSearch.view.adapter.AreaAdapter;
import com.example.mealmate.ui.search.mainSearch.view.adapter.CategoryAdapter;
import com.example.mealmate.ui.search.mainSearch.view.adapter.IngredientAdapter;
import com.example.mealmate.ui.search.mainSearch.view.adapter.OnSearchCardClickListener;
import com.example.mealmate.utils.networkConnection.NetworkResponse;
import com.example.mealmate.utils.networkConnection.NetworkUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SearchFragment extends Fragment implements ISearchView, OnSearchCardClickListener, NetworkResponse {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    CategoryAdapter categoryAdapter;
    IngredientAdapter ingredientAdapter;
    AreaAdapter areaAdapter;
    EditText etSearch;
    View view;
    ChipGroup chipGroup;
    SearchPresenter presenter;
    String filterBy;
    Group group;
    LottieAnimationView lottieAnimationView;


    public SearchFragment() {
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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        presenter = new SearchPresenter(new MealsRepository(MealsRemoteDataSourceImpl.getInstance(),  MealsLocalDataSourceImpl.getInstance(getActivity())), this);


        recyclerView = view.findViewById(R.id.rv_search);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);


        chipGroup = view.findViewById(R.id.chipGroup);
        etSearch = view.findViewById(R.id.et_search);

        group = view.findViewById(R.id.search_group);
        lottieAnimationView = view.findViewById(R.id.lottie_search_animation);


        if(NetworkUtils.isInternetAvailable(getActivity())) {
            onNetworkConncted();
            NetworkUtils.registerNetworkCallback(getActivity(), this);
        }else {
            onNetworkDisconnected();
            NetworkUtils.registerNetworkCallback(getActivity(), this);
        }




        setupFilterChips();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // While text is changing
                String text = charSequence.toString();
                presenter.searchBySection(filterBy,text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });



        return view;
    }




    private void setupFilterChips() {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (chip.getText().toString().equals("Category")) {
                            filterBy = "Category";
                            presenter.getCategories();
                        } else if (chip.getText().toString().equals("Ingredient")) {
                            filterBy = "Ingredient";
                            presenter.getIngredients();
                        } else {
                            filterBy = "Area";
                            presenter.getAreas();
                        }
                    }
                }
            });
        }
    }



    @Override
    public void onCardClick(String sectionName, String itemName) {


        SearchItem searchItem = new SearchItem(sectionName, itemName);
        Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToSectionSearchFragment(searchItem));
    }


    /*    @Override
    public void onCardClick(String sectionName, String itemName) {
        if (sectionName.equals("Category")) {

            SearchItem searchItem = new SearchItem(sectionName, itemName);
            Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToSectionSearchFragment(searchItem));

        } else if (sectionName.equals("Ingredient")) {
            SearchItem searchItem = new SearchItem(sectionName, itemName);
            Navigation.findNavController(view).navigate(SearchFragmentDirections.actionSearchFragmentToSectionSearchFragment(searchItem));
        } else if (sectionName.equals("Area")) {

        }
    }*/

    @Override
    public void showCategoryList(List<CategoryModel> categoryModelList) {
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList, this);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showIngredientsList(List<IngredientModel> ingredientModelList) {
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientModelList, this);
        recyclerView.setAdapter(ingredientAdapter);
    }

    @Override
    public void showAreaList(List<AreaModel> areaModelList) {
        areaAdapter = new AreaAdapter(getActivity(), areaModelList, this);
        recyclerView.setAdapter(areaAdapter);
    }


    @Override
    public void showErrMsg(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkConncted() {
        group.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.GONE);

    }

    @Override
    public void onNetworkDisconnected() {
        group.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
    }
}
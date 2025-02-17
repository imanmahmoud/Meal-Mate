package com.example.mealmate.ui.search.mainSearch.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.model.Area.AreaModel;
import com.example.mealmate.model.ingredient.IngredientModel;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder>{

    private Context context;
    private List<IngredientModel> ingredientModelList;


    public IngredientAdapter(Context context, List<IngredientModel> ingredientModelList) {
        this.context = context;
        this.ingredientModelList = ingredientModelList;
    }

    public void setList(List<IngredientModel> ingredientModelList){
        this.ingredientModelList=ingredientModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IngredientModel currentIngredient = ingredientModelList.get(position);

        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + currentIngredient.getStrIngredient() + ".png")
                .circleCrop()
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        holder.titleTxt.setText(currentIngredient.getStrIngredient());
        holder.cardView.setOnClickListener(v -> {
           // listener.onCardClick("ingredient",currentIngredient.getStrIngredient());

        });

    }



    @Override
    public int getItemCount() {
        return ingredientModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTxt;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_image);
            titleTxt = itemView.findViewById(R.id.search_name);
            cardView= itemView.findViewById(R.id.cv_search_item);
        }
    }
}

package com.example.mealmate.ui.adapter;

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
import com.example.mealmate.R;
import com.example.mealmate.model.meal.MealModel;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {

    private Context context;
    private List<MealModel> mealModelList;
    private OnMealClickListener listener;
    private Boolean isFavItem;


    public MealAdapter(Context context, List<MealModel> mealModelList, OnMealClickListener listener, Boolean isFavItem) {
        this.context = context;
        this.mealModelList = mealModelList;
        this.listener = listener;
        this.isFavItem = isFavItem;
    }

    public void setList(List<MealModel> mealModelList) {
        this.mealModelList = mealModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_meal, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealModel currentMeal = mealModelList.get(position);

        Glide.with(context)
                .load(currentMeal.getStrMealThumb())
                /*.circleCrop()*/
//                .apply(new RequestOptions().override(200, 200))
                /*.placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)*/
                .into(holder.imageView);

        holder.titleTxt.setText(currentMeal.getStrMeal());
        holder.cardView.setOnClickListener(v -> {
            listener.onMealClick(currentMeal);

        });
        if (isFavItem){
            holder.imgFavMinus.setVisibility(View.VISIBLE);
            holder.imgFavMinus.setOnClickListener(v -> {
                listener.onFavMinusClick(currentMeal);
            });

        }
        else{
            holder.imgFavMinus.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mealModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTxt;
        ImageView imgFavMinus;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_meal);
            titleTxt = itemView.findViewById(R.id.tv_meal_name);
            cardView = itemView.findViewById(R.id.cv_meal);
            imgFavMinus = itemView.findViewById(R.id.img_fav_minus_icon);
        }
    }

}

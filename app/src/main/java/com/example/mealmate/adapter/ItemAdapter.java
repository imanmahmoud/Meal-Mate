package com.example.mealmate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private Context context;
    private List<String> ingredients;

    public ItemAdapter(Context context, List<String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    public void setList(List<String> ingredients){
        this.ingredients=ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String current = ingredients.get(position);
        // Load image using Glide
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/" + current + ".png")
                .circleCrop()
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        // Set text
        holder.titleTxt.setText(current);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ingredient_image);
            titleTxt = itemView.findViewById(R.id.ingredient_name);
        }
    }
}


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
import com.example.mealmate.model.categories.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private List<CategoryModel> categories;
    private OnSearchCardClickListener listener;


    public CategoryAdapter(Context context, List<CategoryModel> categories , OnSearchCardClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    public void setList(List<CategoryModel> categories) {
        this.categories = categories;
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
        CategoryModel currentCategory = categories.get(position);

        Glide.with(context)
                .load(currentCategory.getStrCategoryThumb())
                .circleCrop()
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        holder.titleTxt.setText(currentCategory.getStrCategory());
        holder.cardView.setOnClickListener(v -> {
             listener.onCardClick("Category",currentCategory.getStrCategory());

        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTxt;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_image);
            titleTxt = itemView.findViewById(R.id.search_name);
            cardView = itemView.findViewById(R.id.cv_search_item);
        }
    }


}

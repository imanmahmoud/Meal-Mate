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
import com.example.mealmate.ui.adapter.ItemAdapter;
import com.example.mealmate.utils.CountryImage;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.MyViewHolder> {
    private Context context;
    private List<AreaModel> areas;
    private OnSearchCardClickListener listener;

    public AreaAdapter(Context context, List<AreaModel> areas, OnSearchCardClickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
    }

    public void setList(List<AreaModel> areas){
        this.areas=areas;
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
        AreaModel currentArea = areas.get(position);
        // Load image using Glide
        Glide.with(context)
                .load(CountryImage.getFlagUrl(currentArea.getStrArea()))
                .circleCrop()
               /* .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)*/
                .into(holder.imageView);
        // Set text
        holder.titleTxt.setText(currentArea.getStrArea());
        holder.cardView.setOnClickListener(v -> {
             listener.onCardClick("Area",currentArea.getStrArea());

        });

    }




    @Override
    public int getItemCount() {
        return areas.size();
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

package com.example.mealmate.ui.plan.view.dateAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

    private Context context;
    private List<String> dates;

    OnDateCardClickListener listener;

    public DateAdapter(Context context, List<String> dates, OnDateCardClickListener listener) {
        this.context = context;
        this.dates = dates;
        this.listener = listener;
    }

    public void setList(List<String> dates){
        this.dates=dates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_date_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String currentDate = dates.get(position);
        holder.titleTxt.setText(currentDate);
        holder.cardView.setOnClickListener(v -> {
           listener.onDateCardClick(currentDate);

        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.tv_date);
            cardView = itemView.findViewById(R.id.cv_date);
        }
    }
}

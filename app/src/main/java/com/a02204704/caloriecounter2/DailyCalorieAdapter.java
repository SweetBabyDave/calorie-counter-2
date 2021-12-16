package com.a02204704.caloriecounter2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.a02204704.caloriecounter2.models.DailyCalorieEntry;

public class DailyCalorieAdapter extends RecyclerView.Adapter<DailyCalorieAdapter.ViewHolder> {
    ObservableArrayList<DailyCalorieEntry> entries;

    public interface OnDailyCalorieEntryClicked {
        public void onclick(DailyCalorieEntry entry);
    }

    OnDailyCalorieEntryClicked listener;

    public DailyCalorieAdapter(ObservableArrayList<DailyCalorieEntry> entries, OnDailyCalorieEntryClicked listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_calorie_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.food);
        textView.setText(entries.get(position).food);
        holder.itemView.setOnClickListener(view -> {
            listener.onclick(entries.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

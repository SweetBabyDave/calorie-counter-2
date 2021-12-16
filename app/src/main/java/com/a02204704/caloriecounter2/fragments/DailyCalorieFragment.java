package com.a02204704.caloriecounter2.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a02204704.caloriecounter2.DailyCalorieAdapter;
import com.a02204704.caloriecounter2.R;
import com.a02204704.caloriecounter2.fragments.CreateDailyCalorieFragment;
import com.a02204704.caloriecounter2.viewmodels.DailyCalorieViewModel;

public class DailyCalorieFragment extends Fragment {

    public DailyCalorieFragment() {
        super(R.layout.fragment_daily_calories);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DailyCalorieViewModel viewModel = new ViewModelProvider(getActivity())
                .get(DailyCalorieViewModel.class);
        ObservableArrayList dailyCalorieEntries = viewModel.getEntries();
        DailyCalorieAdapter adapter = new DailyCalorieAdapter(dailyCalorieEntries,  entry -> {
            viewModel.setCurrentEntry(entry);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, DailyCalorieEntryFragment.class,null)
                    .setReorderingAllowed(true).addToBackStack(null).commit();
        });

        dailyCalorieEntries.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyItemMoved(fromPosition, toPosition);
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() ->{
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                });
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.list_entries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);

        view.findViewById(R.id.add_food).setOnClickListener(add_food -> {
            viewModel.setCurrentEntry(null);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CreateDailyCalorieFragment.class, null)
                    .setReorderingAllowed(true).addToBackStack(null)
                    .commit();
        });

//        ArrayList<DailyCalorie> dailyCalories = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            DailyCalorie dailyCalorie = new DailyCalorie();
//            dailyCalorie.calories = i;
//            dailyCalorie.food = "Apple" + i;
//            dailyCalories.add(dailyCalorie);
//        }

    }
}

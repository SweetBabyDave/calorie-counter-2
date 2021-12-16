package com.a02204704.caloriecounter2.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.a02204704.caloriecounter2.R;
import com.a02204704.caloriecounter2.viewmodels.DailyCalorieViewModel;

public class DailyCalorieEntryFragment extends Fragment {
    public DailyCalorieEntryFragment() {
        super(R.layout.fragment_daily_calorie_entry);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DailyCalorieViewModel viewModel = new ViewModelProvider(getActivity())
                .get(DailyCalorieViewModel.class);

        viewModel.getCurrentEntry().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                TextView foodView = view.findViewById(R.id.food_view);
                TextView amountView = view.findViewById(R.id.amount_view);

                foodView.setText(entry.food);
                amountView.setText(entry.amount);
            }
        });

        view.findViewById(R.id.delete).setOnClickListener((fab) -> {
            viewModel.deleteCurrentEntry();
        });

        view.findViewById(R.id.edit).setOnClickListener((editFab) -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, CreateDailyCalorieFragment.class, null)
                    .setReorderingAllowed(true).addToBackStack(null).commit();
        });
    }
}

package com.a02204704.caloriecounter2.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.a02204704.caloriecounter2.viewmodels.DailyCalorieViewModel;
import com.a02204704.caloriecounter2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateDailyCalorieFragment extends Fragment {
    private boolean previousSavingState = false;

    public CreateDailyCalorieFragment() {
        super(R.layout.fragment_create_daily_calorie);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DailyCalorieViewModel viewModel = new ViewModelProvider(getActivity())
                .get(DailyCalorieViewModel.class);
        viewModel.getCurrentEntry().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry != null) {
                TextInputEditText foodEditText = view.findViewById(R.id.food_edit_text);
                TextInputEditText amountEditText = view.findViewById(R.id.amount_edit_text);
                foodEditText.setText(entry.food);
                amountEditText.setText(entry.amount);
            }
        });

        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) -> {
            if (saving && !previousSavingState) {
                MaterialButton button = view.findViewById(R.id.save);
                button.setEnabled(false);
                button.setText("Saving...");
                previousSavingState = saving;
            } else if (previousSavingState && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.save).setOnClickListener(save -> {
            TextInputEditText foodEditText = view.findViewById(R.id.food_edit_text);
            TextInputEditText amountEditText = view.findViewById(R.id.amount_edit_text);
            viewModel.saveDailyCalorie(foodEditText.getText().toString(), amountEditText.getText().toString());
        });
    }
}

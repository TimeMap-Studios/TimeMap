package com.example.timemap.ui.currentMonth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentCoffeeBinding;
import com.example.timemap.ui.currentDay.DayViewModel;

public class MonthFragment extends Fragment {
    FragmentCoffeeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MonthViewModel coffeeViewModel =
                new ViewModelProvider(this).get(MonthViewModel.class);
        binding = FragmentCoffeeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

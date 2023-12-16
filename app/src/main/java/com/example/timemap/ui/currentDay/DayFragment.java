package com.example.timemap.ui.currentDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentCoffeeBinding;

public class DayFragment extends Fragment {
    FragmentCoffeeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DayViewModel coffeeViewModel =
                new ViewModelProvider(this).get(DayViewModel.class);
        binding = FragmentCoffeeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

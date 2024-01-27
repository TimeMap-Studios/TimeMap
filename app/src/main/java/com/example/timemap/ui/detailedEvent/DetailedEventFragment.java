package com.example.timemap.ui.detailedEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentDetailedEventBinding;
import com.example.timemap.databinding.FragmentWeekBinding;
import com.example.timemap.ui.currentWeek.WeekViewModel;

/**
 * (View) Events of the week
 **/
public class DetailedEventFragment extends Fragment {
    FragmentDetailedEventBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailedEventViewModel WeekViewModel =
                new ViewModelProvider(this).get(DetailedEventViewModel.class);
        binding = FragmentDetailedEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
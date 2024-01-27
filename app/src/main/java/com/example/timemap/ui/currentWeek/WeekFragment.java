package com.example.timemap.ui.currentWeek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentWeekBinding;
import com.example.timemap.models.CustomDateTime;
import com.example.timemap.ui.eventList.EventListFragment;

/**
 * (View) Events of the week
 **/
public class WeekFragment extends Fragment {
    FragmentWeekBinding binding;
    EventListFragment eventListFragment;
    private CustomDateTime[] week;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WeekViewModel WeekViewModel =
                new ViewModelProvider(this).get(WeekViewModel.class);
        binding = FragmentWeekBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentManager fragmentManager = getParentFragmentManager();

        eventListFragment = new EventListFragment();

        fragmentManager.beginTransaction()
                .add(R.id.weekEventsContainer, eventListFragment)
                .commit();

        week = CustomDateTime.today().currentWeek()[0].previousWeek();
        root.post(new Runnable() {
            @Override
            public void run() {
                loadWeek(week);
            }
        });

        binding.previousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (week == null) return;
                loadWeek(week[0].previousWeek());
            }
        });

        binding.nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (week == null) return;
                loadWeek(week[0].nextWeek());
            }
        });

        return binding.getRoot();
    }

    private void loadWeek(CustomDateTime[] week) {
        if (week == null) return;
        this.week = week;
        binding.currentMonth.setText(week[0].getDay() + " " + week[0].getMonthName() + " - " + week[6].getDay() + " " + week[6].getMonthName());
        binding.currentWeek.setText(week[0].weekOfYear() + "º year's week");
        eventListFragment.addWeek(week);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

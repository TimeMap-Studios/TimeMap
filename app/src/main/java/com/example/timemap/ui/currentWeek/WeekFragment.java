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
import com.example.timemap.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * (View) Events of the week
 **/
public class WeekFragment extends Fragment {
    FragmentWeekBinding binding;
    EventListFragment eventListFragment;
    private Set<CustomDateTime> days;

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

        days = CustomDateTime.now().currentWeek();
        root.post(new Runnable() {
            @Override
            public void run() {
                loadWeek(days);
            }
        });

        binding.previousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreviousWeek();
            }
        });

        binding.nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextWeek();
            }
        });

        OnSwipeTouchListener swipper = new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                loadNextWeek();
            }

            @Override
            public void onSwipeRight() {
                loadPreviousWeek();
            }
        };

        root.setOnTouchListener(swipper);

        return binding.getRoot();
    }

    private void loadWeek(Set<CustomDateTime> week) {
        if (week == null) return;
        this.days = week;
        List<CustomDateTime> days = new ArrayList<>(week);
        CustomDateTime first = days.get(0);
        CustomDateTime last = days.get(6);
        binding.currentMonth.setText(first.getDay() + " " + first.getMonthName() + " - " + last.getDay() + " " + last.getMonthName());
        binding.currentWeek.setText(first.getWeekOfYear() + "º year's week");
        eventListFragment.addDaysWithLabel(week);
    }

    private void loadPreviousWeek() {
        if (days == null) return;
        CustomDateTime first = days.stream().findFirst().orElse(null);
        loadWeek(first.previousWeek());
    }

    private void loadNextWeek() {
        if (days == null) return;
        CustomDateTime first = days.stream().findFirst().orElse(null);
        loadWeek(first.nextWeek());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

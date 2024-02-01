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
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A view representing events of the week. Displays a list of events for the current week with navigation options.
 */
public class WeekFragment extends Fragment {

    // UI components and variables
    FragmentWeekBinding binding;
    EventListFragment eventListFragment;
    private Set<CustomDateTime> days;

    /**
     * onCreateView:
     * Inflates the fragment layout, initializes UI elements, and sets up event handling.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The root view of the fragment.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WeekViewModel WeekViewModel =
                new ViewModelProvider(this).get(WeekViewModel.class);
        binding = FragmentWeekBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the fragment manager
        FragmentManager fragmentManager = getParentFragmentManager();

        // Create and add the EventListFragment to the layout
        eventListFragment = new EventListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.weekEventsContainer, eventListFragment)
                .commit();

        // Load the current week's events
        days = CustomDateTime.now().currentWeek();
        root.post(new Runnable() {
            @Override
            public void run() {
                loadWeek(days);
            }
        });

        // Set up click listeners for navigating to previous and next weeks
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

        // Set up swipe gestures for navigating to previous and next weeks
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

    /**
     * Loads and displays events for the given week, updating UI elements accordingly.
     *
     * @param week A set of CustomDateTime representing the days of the week.
     */
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

    /**
     * Loads and displays events for the previous week, updating UI elements accordingly.
     */
    private void loadPreviousWeek() {
        if (days == null) return;
        CustomDateTime first = days.stream().findFirst().orElse(null);
        loadWeek(first.previousWeek());
    }

    /**
     * Loads and displays events for the next week, updating UI elements accordingly.
     */
    private void loadNextWeek() {
        if (days == null) return;
        CustomDateTime first = days.stream().findFirst().orElse(null);
        loadWeek(first.nextWeek());
    }

    /**
     * Cleans up resources when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

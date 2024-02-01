package com.example.timemap.ui.currentDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentDayBinding;
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.model.EventList;
import com.example.timemap.ui.eventList.EventListFragment;
import com.example.timemap.utils.OnSwipeTouchListener;

/**
 * A view representing events of a specific day. Displays a list of events for the selected day with navigation options.
 */
public class DayFragment extends Fragment {

    // UI components and variables
    EventListFragment eventListFragment;
    private FragmentDayBinding binding;
    private CustomDateTime day;

    /**
     * onCreateView:
     * Inflates the fragment layout, initializes UI elements, and sets up event handling.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialization
        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the fragment manager
        FragmentManager fragmentManager = getParentFragmentManager();

        // Create and add the EventListFragment to the layout
        eventListFragment = new EventListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.dayEventsContainer, eventListFragment)
                .commit();

        // Set the current day initially to the current date
        root.post(new Runnable() {
            @Override
            public void run() {
                setDay(CustomDateTime.now());
            }
        });

        // Set up click listeners for navigating to previous and next days
        binding.previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousDay();
            }
        });
        binding.nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDay();
            }
        });

        // Set up swipe gestures for navigating to previous and next days
        OnSwipeTouchListener swipper = new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onSwipeLeft() {
                nextDay();
            }

            @Override
            public void onSwipeRight() {
                previousDay();
            }
        };

        root.setOnTouchListener(swipper);

        return root;
    }

    /**
     * Sets the selected day, updates UI elements, and loads events for the specified day.
     *
     * @param day The CustomDateTime representing the selected day.
     */
    public void setDay(CustomDateTime day){
        this.day = day;
        eventListFragment.clearEventList();
        binding.currentDay.setText(day.getDayFullString());
        eventListFragment.addEvents(EventList.getInstance().getEventsByDay(day));
    }

    /**
     * Navigates to the previous day, updating UI and loading events accordingly.
     */
    public void previousDay(){
        setDay(day.subtractDays(1));
    }

    /**
     * Navigates to the next day, updating UI and loading events accordingly.
     */
    public void nextDay(){
        setDay(day.addDays(1));
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

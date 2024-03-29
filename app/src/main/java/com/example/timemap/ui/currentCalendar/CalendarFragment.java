package com.example.timemap.ui.currentCalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.example.timemap.databinding.FragmentCalendarBinding;

import com.example.timemap.R;
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.model.Event;
import com.example.timemap.model.EventList;
import com.example.timemap.ui.eventList.EventListFragment;

import java.util.Calendar;
import java.util.Set;

/**
 * Fragment that displays a calendar with events.
 **/
public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding; // Binding for the fragment's layout
    EventListFragment eventListFragment; // Fragment to display a list of events
    FragmentManager fragmentManager; // Manager to handle fragment transactions

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize ViewModel for this fragment
        CalendarViewModel calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        // Inflate the layout for this fragment using binding
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        // Get the FragmentManager for interacting with fragments associated with this fragment's activity
        fragmentManager = getParentFragmentManager();

        // Set up a listener for date changes on the calendar view
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance(); // Create a Calendar instance for the selected date
            selectedDate.set(year, month, dayOfMonth); // Set the selected date
            loadAndDisplayEventsForSelectedDay(selectedDate); // Load and display events for the selected date
        });

        // Initialize the EventListFragment
        eventListFragment = new EventListFragment();

        // Add the EventListFragment to the layout, identified by R.id.eventsContainer
        fragmentManager.beginTransaction()
                .add(R.id.eventsContainer, eventListFragment)
                .commit();

        // Load events for the current day when the view is created
        View root = binding.getRoot();
        root.post(() -> eventListFragment.addEvents(EventList.getInstance().getTodayEvents()));

        return binding.getRoot(); // Return the root view of the binding
    }

    /**
     * Loads and displays events for the specified date.
     * @param date The calendar date for which to load events.
     */
    private void loadAndDisplayEventsForSelectedDay(Calendar date) {
        EventList eventList = EventList.getInstance(); // Assume EventList already has the events loaded
        Set<Event> eventsForSelectedDay = eventList.getEventsByDay(new CustomDateTime(date.getTimeInMillis())); // Get events for the selected day

        eventListFragment.clearEventList(); // Clear the current event list
        eventListFragment.addEvents(eventsForSelectedDay); // Add the events for the selected day to the event list fragment
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear the binding when the view is destroyed to avoid memory leaks
    }
}

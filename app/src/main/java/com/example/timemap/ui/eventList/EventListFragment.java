package com.example.timemap.ui.eventList;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentEventListBinding;
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.model.Event;
import com.example.timemap.model.EventList;
import com.example.timemap.ui.eventDiv.DayLabelFragment;
import com.example.timemap.ui.eventDiv.EventDivFragment;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This fragment is designed to be incorporated into other views to display a list of events
 * with filtering capabilities.
 */
public class EventListFragment extends Fragment {

    // Constants
    public static final String DEFAULT_FILTER = " - - - - - - - - -";

    // UI components and variables
    FragmentManager fragmentManager;
    Spinner spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;
    private FragmentEventListBinding binding;
    private Map<Event, Fragment> events;
    private Set<String> filters;
    private int hidden;
    private Set<CustomDateTime> days;
    private View root;
    private Set<Fragment> dayLabels;
    private boolean loadPastEvents;

    /**
     * Initializes the fragment's UI components and sets up event handling.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadPastEvents = getContext().getSharedPreferences("appPreferences", MODE_PRIVATE).getBoolean("loadPastEvents", true);
        // Initialization
        events = new HashMap<>();
        dayLabels = new HashSet<>();
        EventListViewModel EventListViewModel =
                new ViewModelProvider(this).get(EventListViewModel.class);
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        fragmentManager = getParentFragmentManager();

        // Spinner configuration
        spinner = root.findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        clearFilters(); // Instantiates the filter list and adds the default filters

        // Spinner item selection listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // Handle the selection event
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Manejar el evento de selección aquí
                String selectedFilter = spinnerAdapter.getItem(position).toString();
                filterEvents(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejar el caso cuando no hay ninguna selección, en este caso no es posible
            }
        });

        return root;
    }

    /**
     * Hides the events that do not match the selected filter.
     *
     * @param selectedFilter The filter to be applied.
     */
    private void filterEvents(String selectedFilter) {
        hidden = 0;
        events.forEach((event, fragment) -> {
            if (!event.hasFilter(selectedFilter)) {
                fragmentManager.beginTransaction().hide(fragment).commit();
                hidden++;
            } else {
                fragmentManager.beginTransaction().show(fragment).commit();
            }
        });
        updateHiddenEventNumber();
    }

    /**
     * updateHiddenEventNumber:
     * Updates the UI to display the number of hidden events.
     */
    private void updateHiddenEventNumber() {
        if (hidden == 0) {
            binding.hiddenText.setText("");
        } else {
            binding.hiddenText.setText(hidden + (hidden == 1 ? " Event was hidden" : " Events were hidden"));
        }
    }

    /**
     * Adds days with labels to the event list, displaying associated events.
     * Clears the existing event list and populates it with events from the given days.
     *
     * @param days A set of CustomDateTime representing the days to be added.
     */
    public void addDaysWithLabel(Set<CustomDateTime> days) {
        if (days == null) return;
        clearEventList();
        this.days = days;
        days.forEach(d->{
            Set<Event> dayEventsList = EventList.getInstance().getEventsByDay(d);
            if (dayEventsList.size() > 0) {
                addDayLabel(d.getDayFullString());
                addEvents(dayEventsList);
            }
        });
    }

    /**
     * Adds a DayLabelFragment with the given dayName to the event list.
     * The DayLabelFragment displays the name of the day above associated events.
     *
     * @param dayName The name of the day to be displayed on the label.
     */
    private void addDayLabel(String dayName) {
        DayLabelFragment dayLabelFragment = new DayLabelFragment(requireActivity(), dayName);
        dayLabels.add(dayLabelFragment);
        addFragment(dayLabelFragment);
    }

    /**
     * Adds all the events in the collection
     *
     * @param events The collection of events
     */
    public void addEvents(Collection<Event> events) {
        addEvents(events.toArray(new Event[events.size()]));
    }

    /**
     * Adds as many events as provided as params
     *
     * @param events Events to add to the list
     */
    public void addEvents(Event... events) {
        for (Event e : events) {
            addEvent(e);
        }
    }

    /**
     * Adds an Event to the list
     *
     * @param e Event to add
     * @return false if the list already contains the Event, false if it not
     */
    public boolean addEvent(Event e) {
        if (events.containsKey(e)) return false;
        if(!loadPastEvents){
            if (CustomDateTime.isAnyDayBefore(e.getEndTimeAsCalendar(), Calendar.getInstance())) return false;
        }
        EventDivFragment eventDivFragment = new EventDivFragment(e);
        try {
            fragmentManager.beginTransaction()
                    .add(R.id.eventListLayout, eventDivFragment)
                    .commit();
            events.put(e, eventDivFragment);
            addFilters(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            events.remove(e);
            return false;
        }
        return true;
    }

    /**
     * Adds a generic Fragment to the event list.
     *
     * @param fragment The Fragment to be added to the event list.
     * @return true if the addition is successful, false otherwise.
     */
    public boolean addFragment(Fragment fragment) {
        try {
            fragmentManager.beginTransaction()
                    .add(R.id.eventListLayout, fragment)
                    .commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes an Event from the list
     *
     * @param e Event to delete from the list, then resets the filters
     * @return true if the list contains the Event, false if it not
     */
    public boolean removeEvent(Event e) {
        if (e == null) return false;
        Fragment eventDivFragment = events.get(e);
        if (eventDivFragment == null) return false;
        fragmentManager.beginTransaction().remove(eventDivFragment).commitAllowingStateLoss();
        events.remove(e);
        resetFilters();
        return true;
    }

    /**
     * Clears all Events from the list
     */
    public void clearEventList() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : events.values()) {
            transaction.remove(fragment);
        }
        for (Fragment fragment : dayLabels) {
            transaction.remove(fragment);
        }
        transaction.commitAllowingStateLoss();
        events.clear();
        clearFilters();
    }

    /**
     * Clears the current filters and initializes the filter set with the default filter.
     * Also updates the spinnerAdapter to reflect the changes.
     */
    private void clearFilters() {
        filters = new TreeSet<>();
        spinnerAdapter.clear();
        spinnerAdapter.add(DEFAULT_FILTER);
    }

    /**
     * Resets de filters taking them from the events list
     */
    private void resetFilters() {
        clearFilters();
        events.keySet().forEach(e -> {
            addFilters(e);
        });
    }

    /**
     * Adds the filters from an event
     *
     * @param e The event to get the filters from
     */
    private void addFilters(Event e) {
        e.getFilters().forEach(f -> {
            if (!filters.contains(f)) {
                filters.add(f);
                spinnerAdapter.add(f);
            }
        });
    }

    /**
     * onDestroyView:
     * Cleans up resources when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

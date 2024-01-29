package com.example.timemap.ui.eventList;

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
import com.example.timemap.models.CustomDateTime;
import com.example.timemap.models.Event;
import com.example.timemap.models.EventList;
import com.example.timemap.ui.eventDiv.DayLabelFragment;
import com.example.timemap.ui.eventDiv.EventDivFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * (Component) This fragment can be incorporated in another views
 **/
public class EventListFragment extends Fragment {

    public static final String DEFAULT_FILTER = " - - - - - - - - -";
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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Manejar el evento de selección aquí
                String selectedFilter = spinnerAdapter.getItem(position).toString();
                filterEvents(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Manejar el caso cuando no hay ninguna selección
            }
        });

        return root;
    }

    /**
     * Hides the non selected filter events
     *
     * @param selectedFilter The events with this filter wont be hidden
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

    private void updateHiddenEventNumber() {
        if (hidden == 0) {
            binding.hiddenText.setText("");
        } else {
            binding.hiddenText.setText(hidden + (hidden == 1 ? " Event was hidden" : " Events were hidden"));
        }
    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

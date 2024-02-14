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
import com.example.timemap.databinding.FragmentCalendarBinding;
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.model.Event;
import com.example.timemap.model.EventList;
import com.example.timemap.ui.eventList.EventListFragment;

import java.util.Calendar;
import java.util.Set;

/**
 * (View) Calendar with the events
 **/
public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    EventListFragment eventListFragment;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        fragmentManager = getParentFragmentManager();

        // Configuración del CalendarView para cargar eventos del día seleccionado
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            loadAndDisplayEventsForSelectedDay(selectedDate);
        });

        // Create the EventListFragment

        eventListFragment = new EventListFragment();

        // Create and add the EventListFragment to the layout
        fragmentManager.beginTransaction()
                .add(R.id.eventsContainer, eventListFragment)
                .commit();

        // Cargar eventos para el día actual al iniciar la vista
        View root = binding.getRoot();
        root.post(new Runnable() {
            @Override
            public void run() {
                eventListFragment.addEvents(EventList.getInstance().getTodayEvents());
            }
        });


        return binding.getRoot();
    }


    private void loadAndDisplayEventsForSelectedDay(Calendar date) {
        EventList eventList = EventList.getInstance(); // Asume que EventList ya tiene los eventos cargados
        Set<Event> eventsForSelectedDay = eventList.getEventsByDay(new CustomDateTime(date.getTimeInMillis()));

        eventListFragment.clearEventList();
        eventListFragment.addEvents(eventsForSelectedDay);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


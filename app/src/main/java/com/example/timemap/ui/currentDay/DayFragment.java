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
 * (View) Events of the day
 **/
public class DayFragment extends Fragment {
    EventListFragment eventListFragment;
    private FragmentDayBinding binding;

    private CustomDateTime day;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtiene el FragmentManager del fragmento actual
        FragmentManager fragmentManager = getParentFragmentManager();

        // Crea una instancia del fragmento
        eventListFragment = new EventListFragment();

        // Inicia la transacción del fragmento
        fragmentManager.beginTransaction()
                .add(R.id.dayEventsContainer, eventListFragment)
                .commit();

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

        root.post(new Runnable() {
            @Override
            public void run() {
                setDay(CustomDateTime.now());
            }
        });

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

    public void setDay(CustomDateTime day){
        this.day = day;
        eventListFragment.clearEventList();
        binding.currentDay.setText(day.getDayFullString());
        eventListFragment.addEvents(EventList.getInstance().getEventsByDay(day));
    }

    public void previousDay(){
        setDay(day.subtractDays(1));
    }

    public void nextDay(){
        setDay(day.addDays(1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

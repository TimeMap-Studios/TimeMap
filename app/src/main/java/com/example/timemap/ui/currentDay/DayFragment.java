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
import com.example.timemap.models.EventList;
import com.example.timemap.ui.eventList.EventListFragment;

/**
 * (View) Events of the day
 **/
public class DayFragment extends Fragment {
    EventListFragment eventListFragment;
    private FragmentDayBinding binding;

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

        // Hay que agregar los eventos de esta forma porque si no el fragmento no está todavía creado cando se añaden
        root.post(new Runnable() {
            @Override
            public void run() {
                eventListFragment.addEvents(EventList.getInstance().getTodayEvents());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

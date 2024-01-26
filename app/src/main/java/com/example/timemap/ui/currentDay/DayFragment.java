package com.example.timemap.ui.currentDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentDayBinding;

import com.example.timemap.R;
import com.example.timemap.ui.eventList.EventListFragment;

public class DayFragment extends Fragment {
    private FragmentDayBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Crea una instancia del fragmento
        EventListFragment eventList = new EventListFragment();

        // Obtiene el FragmentManager del fragmento actual
        FragmentManager fragmentManager = getParentFragmentManager();

        // Inicia la transacción del fragmento
        fragmentManager.beginTransaction()
                .replace(R.id.dayEventsContainer, eventList)
                .commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

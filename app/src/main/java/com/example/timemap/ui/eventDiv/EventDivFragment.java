package com.example.timemap.ui.eventDiv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentEventDivBinding;
import com.example.timemap.databinding.FragmentEventListBinding;
import com.example.timemap.models.Event;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EventDivFragment extends Fragment {

    private Event event;
    private FragmentEventDivBinding binding;

    public EventDivFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDivBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Actualizar el TextView con el título
        binding.eventText.setText(event.getName());
        // Actualizar el TextView con el tiempo
        binding.remainingTimeText.setText(event.getEndTime().toString());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
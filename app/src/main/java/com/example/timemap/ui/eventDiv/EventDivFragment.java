package com.example.timemap.ui.eventDiv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentEventListBinding;
import com.example.timemap.models.Event;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class EventDivFragment extends Fragment {

    private Event event;
    public EventDivFragment(Event event) {
        this.event = event;
    }

    private FragmentEventListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 3. Crear un Bundle para pasar datos al fragmento
        Bundle bundle = new Bundle();
        bundle.putString("titulo", event.getName());
        bundle.putString("tiempo", event.getEndTime().toString());
        this.setArguments(bundle);

        EventDivViewModel EventDivViewModel =
                new ViewModelProvider(this).get(EventDivViewModel.class);
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

package com.example.timemap.ui.eventList;

import android.os.Build;
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
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentEventListBinding;
import com.example.timemap.models.Event;
import com.example.timemap.ui.eventDiv.EventDivFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.timemap.R;

public class EventListFragment extends Fragment {

    private List<Event> events;
    private FragmentEventListBinding binding;
    FragmentManager fragmentManager;
    private LinearLayout contenedor;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        events = new ArrayList<Event>();
        EventListViewModel EventListViewModel =
                new ViewModelProvider(this).get(EventListViewModel.class);
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
/*
        fragmentManager = requireParentFragment().getChildFragmentManager();
        // Obtén la referencia al LinearLayout eventListLayout
        contenedor = root.findViewById(R.id.eventListLayout);
*/
        return root;
    }

    public void loadEvent(Event e){
        events.add(e);
        EventDivFragment nuevoFragmento = new EventDivFragment(e);
        // 4. Agregar el fragmento al contenedor
        fragmentManager.beginTransaction()
                .add(contenedor.getId(), nuevoFragmento)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

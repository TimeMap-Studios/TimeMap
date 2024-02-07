package com.example.timemap.ui.allEvents;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentAllEventsBinding;
import com.example.timemap.model.EventList;
import com.example.timemap.ui.eventList.EventListFragment;

/**
 * A view representing all events. Displays a list of all events with labels for each day.
 */
public class AllEventsFragment extends Fragment {
    // UI components and variables
    EventListFragment eventListFragment;
    private FragmentAllEventsBinding binding;

    /**
     * onCreateView:
     * Inflates the fragment layout, initializes UI elements, and sets up event handling.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialization
        binding = FragmentAllEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the fragment manager
        FragmentManager fragmentManager = getParentFragmentManager();

        // Create and add the EventListFragment to the layout
        eventListFragment = new EventListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.allEventsContainer, eventListFragment)
                .commit();

        // Load events for all days
        root.post(new Runnable() {
            @Override
            public void run() {
                eventListFragment.addDaysWithLabel(EventList.getInstance().getDays());
            }
        });

        return root;
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
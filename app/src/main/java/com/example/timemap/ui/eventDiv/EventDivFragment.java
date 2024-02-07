package com.example.timemap.ui.eventDiv;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentEventDivBinding;
import com.example.timemap.model.Event;

/**
 * A fragment component designed to be incorporated into other views, displaying details of an event.
 * The fragment updates the remaining time of the event in real-time.
 */
public class EventDivFragment extends Fragment {

    // Variables
    Thread ticTac; // Thread for updating the remaining time
    private Event event; // Event object associated with this fragment
    private FragmentEventDivBinding binding;
    private boolean isUpdatingTime = true;

    /**
     * Constructor:
     * Initializes the EventDivFragment with the specified event.
     *
     * @param event The Event object to be displayed.
     */
    public EventDivFragment(Event event) {
        this.event = event;
    }

    /**
     * onCreateView:
     * Inflates the fragment layout, initializes UI elements, and starts the thread for updating time.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The root view of the fragment.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDivBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update the event name in the UI
        updateName();

        // Set up click listener to show event details
        binding.eventDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetails();
            }
        });

        // Start the thread to update the remaining time
        startUpdatingTimeThread();

        return root;
    }

    /**
     * Initializes and starts a thread to update the remaining time of the event in real-time.
     */
    private void startUpdatingTimeThread() {
        ticTac = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isUpdatingTime && this != null && !ticTac.isInterrupted()) {
                        try {
                            Thread.sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getActivity() != null) updateTimeRemaining();
                                    else ticTac.interrupt();
                                }
                            });
                        } catch (InterruptedException | NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
        });
        ticTac.start();
    }

    /**
     * Updates the event name in the UI.
     */
    public void updateName() {
        binding.eventText.setText(event.getName());
    }

    /**
     * Navigates to a detailed view of the event when the user clicks on the fragment.
     */
    private void showDetails() {
        View v = this.getView();
        if (v == null) return;
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        Navigation.findNavController(v).navigate(R.id.deatiledEvent, bundle);
    }

    /**
     * Updates the remaining time on the UI, adjusting text color based on whether the time is positive or negative.
     */
    public void updateTimeRemaining() {
        // Actualizar el TextView con el tiempo
        String text = event.getRemainingTime();
        if (text == null || text.trim() == "") return;
        if (text.trim().charAt(0) == '-') {
            binding.remainingTimeText.setTextColor(ContextCompat.getColor(requireContext(), R.color.cold_red));
        } else {
            binding.remainingTimeText.setTextColor(Color.BLACK);
        }
        binding.remainingTimeText.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detener el hilo cuando se destruye la vista
        if (ticTac != null) if (!ticTac.isInterrupted()) ticTac.interrupt();
        isUpdatingTime = false;
        binding = null;
    }
}
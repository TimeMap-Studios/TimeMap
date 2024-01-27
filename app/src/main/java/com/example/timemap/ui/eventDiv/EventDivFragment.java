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

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentEventDivBinding;
import com.example.timemap.models.Event;

/**
 * (Component) This fragment can be incorporated in another views
 **/
public class EventDivFragment extends Fragment {

    Thread ticTac;
    private Event event;
    private FragmentEventDivBinding binding;
    private boolean isUpdatingTime = true;

    public EventDivFragment(Event event) {
        this.event = event;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDivBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        updateName();

        // Iniciar el hilo para actualizar el tiempo restante
        startUpdatingTimeThread();

        return root;
    }

    private void startUpdatingTimeThread() {
        ticTac = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isUpdatingTime && this != null && !ticTac.isInterrupted()) {
                        try {
                            // Esperar un segundo
                            Thread.sleep(1000);

                            // Actualizar el tiempo restante en el hilo principal
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getActivity() != null) updateTimeRemaining();
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

    public void updateName() {
        // Actualizar el TextView con el título
        binding.eventText.setText(event.getName());
    }

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
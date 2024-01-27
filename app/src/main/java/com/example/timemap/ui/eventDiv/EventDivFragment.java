package com.example.timemap.ui.eventDiv;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemap.databinding.FragmentEventDivBinding;
import com.example.timemap.models.Event;

/**
 * (Component) This fragment can be incorporated in another views
 **/
public class EventDivFragment extends Fragment {

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isUpdatingTime) {
                    try {
                        // Esperar un segundo
                        Thread.sleep(1000);

                        // Actualizar el tiempo restante en el hilo principal
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTimeRemaining();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
            binding.remainingTimeText.setTextColor(Color.RED);
        } else {
            binding.remainingTimeText.setTextColor(Color.BLACK);
        }
        binding.remainingTimeText.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detener el hilo cuando se destruye la vista
        isUpdatingTime = false;
        binding = null;
    }
}
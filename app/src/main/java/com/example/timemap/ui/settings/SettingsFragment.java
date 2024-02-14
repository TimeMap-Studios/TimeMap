package com.example.timemap.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.databinding.FragmentSettingsBinding;
import com.example.timemap.utils.ConfirmationDialog;
import com.example.timemap.utils.SessionManager;

/**
 * A Fragment that represents the settings screen in the application.
 * This class is responsible for displaying and managing user settings.
 */
public class SettingsFragment extends Fragment{
    // View Binding variable for the fragment's layout
    FragmentSettingsBinding binding;

    private Button logoutButton;

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the inflated layout.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create an instance of the SettingsViewModel using ViewModelProvider
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        // Inflate the layout for this fragment using View Binding
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        logoutButton = binding.buttonLogout;

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialog.askForConfirmation(requireContext(), "Are you sure you want to log out?", new ConfirmationDialog.ConfirmationCallback() {
                    @Override
                    public void onConfirmation(boolean confirmed) {
                        if (confirmed) {
                            SessionManager.getInstance().clearCurrentSession();
                            // Load the login fragment
                            LoginActivity.getInstance().loadLoginFragment();
                        }
                    }
                });
            }
        });

        // Return the root view of the inflated layout
        return binding.getRoot();
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     * The fragment's view hierarchy is destroyed here.
     */
    @Override
    public void onDestroyView() {
        // Call the superclass method to perform the default destruction of the view
        super.onDestroyView();
        // Set the View Binding variable to null to release the reference and avoid potential memory leaks
        binding = null;
    }
}
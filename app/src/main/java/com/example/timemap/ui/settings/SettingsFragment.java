package com.example.timemap.ui.settings;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentSettingsBinding;
import com.example.timemap.db.DatabaseController;
import com.example.timemap.utils.ConfirmationDialog;
import com.example.timemap.utils.SessionManager;

import java.io.IOException;
import java.util.List;

/**
 * A Fragment that represents the settings screen in the application.
 * This class is responsible for displaying and managing user settings.
 */
public class SettingsFragment extends Fragment{
    // View Binding variable for the fragment's layout
    FragmentSettingsBinding binding;

    private Button logoutButton, removeAccountButton, changePassword, copyDb;
    private Toast timemapToast;
    private TextView toastText;

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
        removeAccountButton = binding.removeUserButton;
        changePassword = binding.editPassButton;
        copyDb = binding.downloadButton;

        // Set up the toast for displaying messages
        timemapToast = new Toast(LoginActivity.getInstance().getApplicationContext());
        timemapToast.setDuration(Toast.LENGTH_SHORT);
        timemapToast.setView(inflater.inflate(R.layout.timemap_toast, (ViewGroup) LoginActivity.getInstance().findViewById(R.id.toastContainer)));
        toastText = timemapToast.getView().findViewById(R.id.toastMessage);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialog.askForConfirmation(requireContext(), "Are you sure you want to log out?", new ConfirmationDialog.ConfirmationCallback() {
                    @Override
                    public void onConfirmation(boolean confirmed) {
                        if (confirmed) {
                            SessionManager.getInstance().clearCurrentSession();
                            Intent intent = new Intent(MainActivity.instance.getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.instance.getApplicationContext(), R.anim.login_activity_enter, 0);
                            MainActivity.instance.getApplicationContext().startActivity(intent, options.toBundle());
                        }
                    }
                });
            }
        });

        removeAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationDialog.askForConfirmation(requireContext(), "ARE YOU SURE YOU WANT TO DELETE YOUR ACCOUNT? THIS ACTION CANNOT BE UNDONE", new ConfirmationDialog.ConfirmationCallback() {
                    @Override
                    public void onConfirmation(boolean confirmed) {
                        if (confirmed) {
                            UserController.getInstance().removeUser(UserController.getInstance().getCurrentUser());
                            SessionManager.getInstance().clearCurrentSession();
                            Intent intent = new Intent(MainActivity.instance.getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.instance.getApplicationContext(), R.anim.login_activity_enter, 0);
                            MainActivity.instance.getApplicationContext().startActivity(intent, options.toBundle());
                        }
                    }
                });
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.getNavController().navigate(R.id.changePassFragment);
            }
        });

        copyDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserController.getInstance().getDbController().getHelper().copyDataBaseToDownloads();
                    toastText.setText("Database downloaded. Check your device downloads folder");
                    timemapToast.show();
                } catch (IOException e) {
                    toastText.setText("Couldn't download Database");
                    timemapToast.show();
                }
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
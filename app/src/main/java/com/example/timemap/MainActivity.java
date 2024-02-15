package com.example.timemap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.timemap.databinding.ActivityMainBinding;
import com.example.timemap.utils.ConfirmationDialog;
import com.google.android.material.navigation.NavigationView;

/**
 * The main activity representing the entry point of the application.
 */
public class MainActivity extends AppCompatActivity implements ConfirmationDialog.ConfirmationCallback {

    // Instance variable for singleton pattern
    public static MainActivity instance;
    // Flag indicating whether a new event is being created
    public boolean creatingEvent;
    // UI components and variables
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private View newEventButton;

    /**
     * Called when the activity is first created. Responsible for initializing the activity, setting up UI elements,
     * and handling the creation of new events.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Initialize the instance variable for singleton pattern
        instance = this;

        // Inflate the activity layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the action bar for the activity
        setSupportActionBar(binding.appBarMain.toolbar);

        // Get references to UI components
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Set up the navigation drawer and configure top-level destinations
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_day, R.id.nav_week, R.id.nav_calendar, R.id.nav_events, R.id.nav_information)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set up the click listener for the new event button in the action bar
        newEventButton = binding.appBarMain.toolbar.findViewById(R.id.action_add);
        if (newEventButton != null) {
            newEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickNewEvent();
                }
            });
        }
    }

    /**
     * Initialize the contents of the Activity's options menu.
     *
     * @param menu The options menu in which items are placed.
     * @return True if the menu should be displayed; false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles the click event to exit the lateral menu by closing it.
     *
     * @param v The view that received the click event.
     */
    public void clickExitLateralMenu(View v) {
        // Cierra el menú lateral
        binding.drawerLayout.closeDrawers();
    }


    /**
     * Handles the click event for the "New Event" button in the action bar. Navigates to the detailed event screen.
     * Checks if there is an ongoing event creation and prompts for confirmation if needed.
     */
    public void clickNewEvent() {
        if (navController == null) return;
        if (creatingEvent) {
            ConfirmationDialog.askForConfirmation(this, "Estas editando un evento. ¿Deseas crear uno nuevo?", this);
            return;
        }
        navController.navigate(R.id.deatiledEvent);

    }

    /**
     * Handle Up navigation by passing it to the navigation controller.
     *
     * @return True if navigation was handled, false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Callback method from the ConfirmationDialog.ConfirmationCallback interface. Invoked when the user confirms or denies the action.
     *
     * @param confirmed True if the user confirms, false if denied.
     */
    @Override
    public void onConfirmation(boolean confirmed) {
        if (confirmed)
            navController.navigate(R.id.deatiledEvent);
    }
}
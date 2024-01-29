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

public class MainActivity extends AppCompatActivity implements ConfirmationDialog.ConfirmacionCallback {
    public static MainActivity instance;
    public boolean creatingEvent;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    private View newEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        instance = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_day, R.id.nav_week, R.id.nav_calendar, R.id.nav_events, R.id.nav_information)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Ocultar el menú lateral al hacer click
    public void clickExitLateralMenu(View v) {
        // Cierra el menú lateral
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }


    public void clickNewEvent() {
        if (navController == null) return;
        if (creatingEvent) {
            ConfirmationDialog.askForConfirmation(this, "Estas editando un evento. ¿Deseas crear uno nuevo?", this);
            return;
        }
        navController.navigate(R.id.deatiledEvent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onConfirmacion(boolean confirmado) {
        if (confirmado)
            navController.navigate(R.id.deatiledEvent);
    }
}
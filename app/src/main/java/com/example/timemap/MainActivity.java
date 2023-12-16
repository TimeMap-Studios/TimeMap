package com.example.timemap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.timemap.databinding.ActivityMainBinding;
import com.example.timemap.ui.coffee.CoffeeFragment;
import com.example.timemap.ui.coffee.CoffeeViewModel;
import com.example.timemap.ui.currentDay.DayViewModel;
import com.example.timemap.ui.currentMonth.MonthViewModel;
import com.example.timemap.ui.currentWeek.WeekViewModel;
import com.example.timemap.ui.information.InfoViewModel;
import com.example.timemap.ui.slideshow.SlideshowViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CoffeeViewModel coffeView = new ViewModelProvider(this).get(CoffeeViewModel.class);
        DayViewModel dayView = new ViewModelProvider(this).get(DayViewModel.class);
        MonthViewModel monthView = new ViewModelProvider(this).get(MonthViewModel.class);
        WeekViewModel weekView = new ViewModelProvider(this).get(WeekViewModel.class);
        InfoViewModel infoView = new ViewModelProvider(this).get(InfoViewModel.class);
        SlideshowViewModel slideshowView = new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_day, R.id.nav_week, R.id.nav_calendar, R.id.nav_information)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void buyMeButtonClicked(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, new CoffeeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Ocultar el menú lateral al hacer click
    public void clickExitLateralMenu(View v) {
        binding.navView.setVisibility(View.GONE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
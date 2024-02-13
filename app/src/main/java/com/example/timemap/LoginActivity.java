package com.example.timemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.timemap.databinding.ActivityLoginBinding;
import com.example.timemap.ui.login.LoginFragment;
import com.example.timemap.ui.register.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance;
    private ActivityLoginBinding binding;
    private LinearLayout container;
    private FragmentManager fragmentManager;

    public static LoginActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        container = binding.loginContainer;

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.loginContainer, new LoginFragment())
                .commit();
    }

    public void loadRegisterFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.loginContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }
}
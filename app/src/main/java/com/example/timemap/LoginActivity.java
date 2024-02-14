package com.example.timemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.ActivityLoginBinding;
import com.example.timemap.ui.login.LoginFragment;
import com.example.timemap.ui.register.RegisterFragment;
import com.example.timemap.utils.SessionManager;

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

        //si no hay un usuario logueado muestra el fragmento de login
        if(SessionManager.getInstance().emptySession()){
            fragmentManager.beginTransaction()
                    .add(R.id.loginContainer, new LoginFragment())
                    .commit();
        }
        else{ //si hay un usuario logueado carga su sesion
            UserController.getInstance().setCurrentUser(SessionManager.getInstance().getSessionUser());
            Log.e("LoginActivity","logged user: "+UserController.getInstance().getCurrentUser().getUsername());
            Intent intent = new Intent(LoginActivity.getInstance().getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void loadRegisterFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.loginContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    public void loadLoginFragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.loginContainer, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
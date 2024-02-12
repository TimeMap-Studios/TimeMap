package com.example.timemap.ui.login;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentLoginBinding;
import com.example.timemap.model.User;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    private Button loginButton;
    private Button registerButton;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(MainActivity.instance != null) MainActivity.instance.creatingEvent = true;

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        loginButton = binding.btnLogin;
        registerButton = binding.btnRegister;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica de inicio de sesión
                // Consultar base de datos para verificar credenciales
                User currentUser = UserController.getInstance().getCurrentUser(binding.introducePass.getText().toString(), binding.introduceUser.getText().toString());
                if(currentUser==null){
                    Toast.makeText(MainActivity.instance.getApplicationContext(), "No existe", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.instance.getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    //abrir ventana de usuario---
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica de registro
                // abrir ventana de registro
            }
        });

        return binding.getRoot();
    }

}


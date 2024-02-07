package com.example.timemap.ui.login;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentCalendarBinding;
import com.example.timemap.ui.currentCalendar.CalendarViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        // Aquí puedes inicializar tus vistas y botones, y asignarles acciones

        return root;
    }

}


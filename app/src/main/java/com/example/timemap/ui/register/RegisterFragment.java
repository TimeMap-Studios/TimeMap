package com.example.timemap.ui.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.databinding.FragmentLoginBinding;
import com.example.timemap.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    FragmentRegisterBinding binding;
    private Button registerButton;
    private EditText username;
    private EditText email;
    private EditText firstPass;
    private EditText secondPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(MainActivity.instance != null) MainActivity.instance.creatingEvent = true;

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        registerButton = binding.registroButton;
        username = binding.newUserTextBox;
        email = binding.emailTextBox;
        firstPass = binding.firstPassTextBox;
        secondPass = binding.secondPassTextBox;
        if(registerButton!=null){
            registerButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    // recoger datos y comprobar
                    checkEmptyFields();
                }
            });
        }

        return binding.getRoot();
    }

    private boolean checkEmptyFields(){
        if(username.getText().toString().isEmpty() || email.getText().toString().isEmpty() || firstPass.getText().toString().isEmpty() || secondPass.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.instance.getApplicationContext(), "Fill the empty fields to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!firstPass.getText().toString().equalsIgnoreCase(secondPass.getText().toString())){
            Toast.makeText(MainActivity.instance.getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            Toast.makeText(MainActivity.instance.getApplicationContext(), "Wrong e-mail format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

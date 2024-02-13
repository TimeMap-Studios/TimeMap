package com.example.timemap.ui.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentLoginBinding;
import com.example.timemap.databinding.FragmentRegisterBinding;

/*
* Fragment for the registration view.
* */
public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    FragmentRegisterBinding binding;
    private Button registerButton;
    private EditText username;
    private EditText email;
    private EditText firstPass;
    private EditText secondPass;
    private Toast timemapToast;
    private TextView toastText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(MainActivity.instance != null) MainActivity.instance.creatingEvent = true;

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        // Get references to UI components
        registerButton = binding.registroButton;
        username = binding.newUserTextBox;
        email = binding.emailTextBox;
        firstPass = binding.firstPassTextBox;
        secondPass = binding.secondPassTextBox;

        // Set up the toast for displaying messages
        timemapToast = new Toast(MainActivity.instance.getApplicationContext());
        timemapToast.setDuration(Toast.LENGTH_SHORT);
        timemapToast.setView(inflater.inflate(R.layout.timemap_toast, (ViewGroup) MainActivity.instance.findViewById(R.id.toastContainer)));
        toastText = timemapToast.getView().findViewById(R.id.toastMessage);

        if(registerButton!=null){
            registerButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    // recoger datos y comprobar
                    if(checkEmptyFields()){
                        UserController.getInstance().registerNewUser(username.getText().toString(),email.getText().toString(),firstPass.getText().toString());
                        // proceder al login
                    }
                }
            });
        }

        return binding.getRoot();
    }

    // Validation for the registration form fields
    private boolean checkEmptyFields(){
        if(username.getText().toString().isEmpty() || email.getText().toString().isEmpty() || firstPass.getText().toString().isEmpty() || secondPass.getText().toString().isEmpty()){
            toastText.setText("Fill the empty fields to continue");
            timemapToast.show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            toastText.setText("Wrong e-mail format");
            timemapToast.show();
            return false;
        }
        else if(!firstPass.getText().toString().equalsIgnoreCase(secondPass.getText().toString())){
            toastText.setText("Passwords must match");
            timemapToast.show();
            return false;
        }
        else if (UserController.getInstance().emailExists(email.getText().toString())) {
            toastText.setText("This e-mail is already registered");
            timemapToast.show();
            return false;
        }
        else if(UserController.getInstance().usernameExists(username.getText().toString())){
            toastText.setText("Unavailable username");
            timemapToast.show();
            return false;
        }
        return true;
    }
}

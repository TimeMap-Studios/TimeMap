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

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentRegisterBinding;

/*
* Fragment for the registration view.
* */
public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;
    FragmentRegisterBinding binding;
    private Button registerButton;
    private Button backButton;
    private EditText username;
    private EditText email;
    private EditText firstPass;
    private EditText secondPass;
    private Toast timemapToast;
    private TextView toastText;

    /**
     * Carga el fragmento de registro, recoge componentes UI del xml y asigna eventos.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(MainActivity.instance != null) MainActivity.instance.creatingEvent = true;

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        // Get references to UI components
        registerButton = binding.registroButton;
        backButton = binding.backToLoginButton;
        username = binding.newUserTextBox;
        email = binding.emailTextBox;
        firstPass = binding.firstPassTextBox;
        secondPass = binding.secondPassTextBox;

        // Set up the toast for displaying messages
        timemapToast = new Toast(LoginActivity.getInstance().getApplicationContext());
        timemapToast.setDuration(Toast.LENGTH_SHORT);
        timemapToast.setView(inflater.inflate(R.layout.timemap_toast, (ViewGroup) LoginActivity.getInstance().findViewById(R.id.toastContainer)));
        toastText = timemapToast.getView().findViewById(R.id.toastMessage);

        registerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // recoger datos y comprobar
                if(validateSubmit()){
                    UserController.getInstance().registerNewUser(username.getText().toString(),email.getText().toString(),firstPass.getText().toString());
                    // proceder al login
                    toastText.setText("New user registered. Please login to continue");
                    timemapToast.show();
                    LoginActivity.getInstance().loadLoginFragment();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.getInstance().loadLoginFragment();
            }
        });
        return binding.getRoot();
    }

    /**
     * Método que recoge la validación del formulario de registro. Muestra mensajes a través del toast personalizado.
     * @return true si supera la validacion
     */
    private boolean validateSubmit(){
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

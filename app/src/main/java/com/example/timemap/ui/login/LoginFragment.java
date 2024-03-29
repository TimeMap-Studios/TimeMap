package com.example.timemap.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentLoginBinding;
import com.example.timemap.model.User;
import com.example.timemap.ui.register.RegisterFragment;
import com.example.timemap.utils.SessionManager;

/**
 * Fragment representing the login screen of the application.
 * */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private Button loginButton;
    private Button registerButton;
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
     * @return view del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Get references to UI components
        loginButton = binding.btnLogin;
        registerButton = binding.btnRegister;

        // Custom toast
        timemapToast = new Toast(LoginActivity.getInstance().getApplicationContext());
        timemapToast.setDuration(Toast.LENGTH_SHORT);
        timemapToast.setView(inflater.inflate(R.layout.timemap_toast, (ViewGroup) LoginActivity.getInstance().findViewById(R.id.toastContainer)));
        toastText = timemapToast.getView().findViewById(R.id.toastMessage);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verify user exists in database
                User currentUser = UserController.getInstance().getLoginUser(binding.introducePass.getText().toString(), binding.introduceUser.getText().toString());
                if(currentUser==null){
                    toastText.setText("incorrect username or password");
                    timemapToast.show();
                }
                else{
                    //set currentUser in UserController to currentUser
                    UserController.getInstance().setCurrentUser(currentUser);
                    SessionManager.getInstance().saveCurrentSession(currentUser);
                    Log.e("LoginActivity","logged user: "+UserController.getInstance().getCurrentUser().getUsername());
                    //abrir ventana de usuario
                    Intent intent = new Intent(LoginActivity.getInstance().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abrir ventana de registro
                LoginActivity.getInstance().loadRegisterFragment();
            }
        });

        return binding.getRoot();
    }

}


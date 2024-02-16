package com.example.timemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.ActivityLoginBinding;
import com.example.timemap.ui.login.LoginFragment;
import com.example.timemap.ui.register.RegisterFragment;
import com.example.timemap.utils.SessionManager;

/**
 * Activity que agrupa las vistas de Login y registro. Realiza lógica de sesión.
 */
public class LoginActivity extends AppCompatActivity {

    private static LoginActivity instance;
    private FragmentManager fragmentManager;

    public static LoginActivity getInstance() {
        return instance;
    }

    /**
     * On create redirige a MainActivity si hay un suario logeado. De lo contrario carga fragment de login para pedir credenciales de acceso.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();

        //si no hay un usuario logueado muestra el fragmento de login
        if(SessionManager.getInstance().emptySession()){
            Log.e("user empty session:", (UserController.getInstance().getCurrentUser()==null)+"");
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

    /**
     * Sustituye el fragmento actual por el fragmento de registro
     */
    public void loadRegisterFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.loginContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }
    /**
     * Sustituye el fragmento actual por el fragmento de login
     */
    public void loadLoginFragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.loginContainer, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
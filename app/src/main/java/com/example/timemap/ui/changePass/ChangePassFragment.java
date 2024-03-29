package com.example.timemap.ui.changePass;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.timemap.LoginActivity;
import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentChangepasswordBinding;
import com.example.timemap.utils.SessionManager;

/**
 * Fragmento que contiene eventos y lógica para la vista de Cambiar Contraseña
 */
public class ChangePassFragment extends Fragment {
    FragmentChangepasswordBinding binding;

    private Button done;

    private EditText oldPass, newPass, repeatPass;
    private Toast timemapToast;
    private TextView toastText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentChangepasswordBinding.inflate(inflater, container, false);

        done = binding.doneButton;
        oldPass = binding.oldPassTextBox;
        newPass = binding.newPassTextBox;
        repeatPass = binding.repeatNewPassTextBox;

        // Set up the toast for displaying messages
        timemapToast = new Toast(LoginActivity.getInstance().getApplicationContext());
        timemapToast.setDuration(Toast.LENGTH_SHORT);
        timemapToast.setView(inflater.inflate(R.layout.timemap_toast, (ViewGroup) LoginActivity.getInstance().findViewById(R.id.toastContainer)));
        toastText = timemapToast.getView().findViewById(R.id.toastMessage);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDone()){
                    UserController.getInstance().getCurrentUser().setPass(newPass.getText().toString());
                    UserController.getInstance().updateUser(UserController.getInstance().getCurrentUser());
                    SessionManager.getInstance().clearCurrentSession();
                    Intent intent = new Intent(MainActivity.instance.getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(MainActivity.instance.getApplicationContext(), R.anim.login_activity_enter, 0);
                    MainActivity.instance.getApplicationContext().startActivity(intent, options.toBundle());
                    toastText.setText("New password has been set");
                    timemapToast.show();
                }
            }
        });

        return binding.getRoot();
    }

    /**
     * Validacion del formulario de cambio de contraseña
     * @return true si ha superado la validacion
     */
    public boolean validateDone(){
        if(oldPass.getText().toString().isEmpty() || newPass.getText().toString().isEmpty() || repeatPass.getText().toString().isEmpty()){
            toastText.setText("Fill the empty fields to continue");
            timemapToast.show();
            return false;
        }
        else if(!newPass.getText().toString().equalsIgnoreCase(repeatPass.getText().toString())){
            toastText.setText("Passwords must match");
            timemapToast.show();
            return false;
        }
        else if(UserController.getInstance().getLoginUser(oldPass.getText().toString(),UserController.getInstance().getCurrentUser().getUsername()) == null){
            toastText.setText("Wrong password");
            timemapToast.show();
            return false;
        }
        return true;
    }
}

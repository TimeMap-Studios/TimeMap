package com.example.timemap;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // Asegúrate de usar el layout correcto

        // Encuentra las vistas por ID
        TextView textView1 = findViewById(R.id.textViewCarga1);
        TextView textView2 = findViewById(R.id.textViewCarga2);

        // Inicia las animaciones
        textView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_to_center));
        textView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_right_to_center));

        // Continúa con el resto de tu lógica de SplashScreen, como un temporizador para pasar a LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cuando el tiempo se acabe, inicia LoginActivity
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                //ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.login_activity_enter, 0);
                //startActivity(intent,options.toBundle());
                startActivity(intent);
                finish(); // Finaliza SplashScreenActivity para que el usuario no pueda volver a ella
            }
        }, 4000);
    }
}

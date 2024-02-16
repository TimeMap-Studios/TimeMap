package com.example.timemap;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Encuentra las vistas por ID
        final TextView textView1 = findViewById(R.id.textViewCarga1);
        final TextView textView2 = findViewById(R.id.textViewCarga2);

        // Inicia las animaciones iniciales
        textView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_to_center));
        textView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_right_to_center));

        // Prepara una animación de desvanecimiento para ser usada más tarde
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        // Handler para retrasar la transición a LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Aplica la animación de desvanecimiento al final del temporizador
                Animation.AnimationListener fadeOutListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Cambia el color a transparente (esto podría no ser visible si inmediatamente se inicia otra actividad)
                        textView1.setTextColor(Color.TRANSPARENT);
                        textView2.setTextColor(Color.TRANSPARENT);

                        // Inicia LoginActivity después de que el texto se haya desvanecido
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                };

                // Establece el listener a la animación y comienza la animación de desvanecimiento
                fadeOut.setAnimationListener(fadeOutListener);
                textView1.startAnimation(fadeOut);
                textView2.startAnimation(fadeOut);
            }
        }, 3000); // Este tiempo incluye la duración de tus animaciones iniciales más cualquier retraso deseado
    }
}

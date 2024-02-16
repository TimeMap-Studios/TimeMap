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

        // Find views by ID
        final TextView textView1 = findViewById(R.id.textViewCarga1);
        final TextView textView2 = findViewById(R.id.textViewCarga2);

        // Start initial animations
        textView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_to_center));
        textView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_right_to_center));

        // Prepare a fade-out animation to be used later
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        // Handler to delay the transition to LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Apply the fade-out animation at the end of the timer
                Animation.AnimationListener fadeOutListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Change the color to transparent (this might not be visible if another activity is immediately started)
                        textView1.setTextColor(Color.TRANSPARENT);
                        textView2.setTextColor(Color.TRANSPARENT);

                        // Start LoginActivity after the text has faded
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                };

                // Set the listener to the animation and start the fade-out animation
                fadeOut.setAnimationListener(fadeOutListener);
                textView1.startAnimation(fadeOut);
                textView2.startAnimation(fadeOut);
            }
        }, 3000); // Time delayed
    }
}

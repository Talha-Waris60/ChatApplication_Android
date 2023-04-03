package com.devdroiddev.realtimechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView messageLogoImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        messageLogoImg = findViewById(R.id.message_logo);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        messageLogoImg.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(SplashScreen.this, AuthenticationActivity.class);
                startActivity(intent);
                finish(); // Call finish() to close the splash activity
            }
        }, 6000);
    }
}
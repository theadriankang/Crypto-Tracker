package com.sp.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide(); // Hide the top default bar


        /*
        ImageView adrian_signature = findViewById(R.id.adrian_signature);
        ImageView coinama_special_logo = findViewById(R.id.coinama_special_logo);
        TextView welcomeMessage1 = findViewById(R.id.welcomeMessage1);
        TextView welecomeMessage2 = findViewById(R.id.welcomeMessage2);
        TextView developerSign = findViewById(R.id.developerSign);
         */


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);




    }
}
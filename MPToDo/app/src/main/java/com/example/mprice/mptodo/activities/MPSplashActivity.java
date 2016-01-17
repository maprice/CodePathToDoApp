package com.example.mprice.mptodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mprice on 1/16/16.
 */
public class MPSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MPSplashActivity.this, MPMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }



}
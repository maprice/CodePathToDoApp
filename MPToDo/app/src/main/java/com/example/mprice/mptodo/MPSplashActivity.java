package com.example.mprice.mptodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mprice on 1/16/16.
 */
public class MPSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MPMainActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.example.androidhigh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btnScan = findViewById(R.id.btnScan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
//                finish();
//            }
//        }, 3000);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Welcome_screenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {
                startActivity(new Intent(Welcome_screenActivity.this,Login_Screen.class));
            }
        }, 3000);
    }

}

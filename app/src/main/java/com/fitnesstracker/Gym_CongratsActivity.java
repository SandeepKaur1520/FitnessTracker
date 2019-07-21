package com.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fitnesstracker.database.DatabaseHelper;

public class Gym_CongratsActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym__congrats);
        Intent intent = getIntent();
        String Distance = intent.getStringExtra("dist" );
        String Time = intent.getStringExtra("tTime");
        String Speed = intent.getStringExtra("speed");
        String Calories = intent.getStringExtra("cal");
        String email = intent.getStringExtra("email");
        TextView time = findViewById(R.id.tvTime);
        TextView  distance = findViewById(R.id.tvDistance);
        TextView speed = findViewById(R.id.tvSpeed);

        time.setText(Time);
        distance.setText(Distance);
        speed.setText(Speed);

        Log.e("Distance = ",Distance);
        Log.e("Time = ",Time+"");
        Log.e("Speed = ",Speed);
        Log.e("Calories = ",Calories);
        Boolean Status = db.updateCalories(email,Calories) ;


    }
}

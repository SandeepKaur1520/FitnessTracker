package com.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Calories extends AppCompatActivity {
    int completed = 0;
    int target = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        Intent intent = getIntent();
        completed = (int) intent.getIntExtra("calValue",0);
        String CalAchive = intent.getStringExtra("calAcheive");
        if(CalAchive!=null){
            target = Integer.parseInt(CalAchive);
        }else{
            target=1000;
        }

        updateChart(completed,target);
    }

    private int updateChart(int completed , int target){
        int percentage = 0;
        Log.e(" Completed : ",String.valueOf(completed));
        percentage = (completed * 100)/target;
        TextView numberOfCals = findViewById(R.id.numberCalories);
        numberOfCals.setText(percentage+"% Completed");
        ProgressBar pieChart = findViewById(R.id.statsPB);
        int progress = (int) percentage;
        pieChart.setProgress(progress);
        return progress;
    }
}

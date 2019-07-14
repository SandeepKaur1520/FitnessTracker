package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class stepsChart extends AppCompatActivity {
    int completed = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_chart);
        Intent intent = getIntent();
        completed = intent.getIntExtra("stepValue",0);

        int target = 1000;

        updateChart(completed,target);
    }

    private int updateChart(int completed , int target){
        int percentage = 0;
        percentage = (completed * 100)/target;
        TextView numberOfCals = findViewById(R.id.number_of_calories);
        numberOfCals.setText(percentage+"% Completed");
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        int progress = (int) percentage;
        pieChart.setProgress(progress);
        return progress;
    }
}

package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class stepsChart extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_chart);
        int completed = 500;
        int target = 2000;

        updateChart(completed,target);
    }

    private int updateChart(int completed , int target){
        int percentage = 0;
        percentage = (completed * 100)/target;
        TextView numberOfCals = findViewById(R.id.number_of_calories);
        numberOfCals.setText(String.valueOf(percentage)+"% Completed");

        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        int progress = (int) percentage;
        pieChart.setProgress(progress);
        return progress;
    }
}

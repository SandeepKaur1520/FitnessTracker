package com.fitnesstracker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;
import com.fitnesstracker.gymfragment.GymHomepageFragment;

public class Daily_Goals extends AppCompatActivity {
    String steps,water,sleep,calories,email;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily__goals);
        SeekBar seekSteps =findViewById(R.id.seekStepGoals);
        SeekBar seekCal =findViewById(R.id.seekCalories);
        SeekBar seekSleep =findViewById(R.id.seekSleep);
        SeekBar seekWater =findViewById(R.id.seekWater);

       steps = "1000";
       water = "20";
       sleep = "24";
       calories = "1000";
        Intent intent = getIntent();
        final TextView tvStep =findViewById(R.id.tvStepGoals);
        final TextView tvWater =findViewById(R.id.tvWater);
        final TextView tvSleep =findViewById(R.id.tvSleep);
        final TextView tvCal =findViewById(R.id.tvCalories);
        Button setGoalBtn = findViewById(R.id.setGoalsBtn);
        email = intent.getStringExtra("email");

        seekSteps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                steps=String.valueOf(seekBar.getProgress());
                tvStep.setText("Step Goals          "+steps);
                Log.e("Steps",String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                steps=String.valueOf(seekBar.getProgress());
                tvStep.setText("Step Goals          "+steps);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                steps=String.valueOf(seekBar.getProgress());
                tvStep.setText("Step Goals          "+steps);

            }
        });
        seekSleep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sleep=String.valueOf(seekBar.getProgress());
                tvSleep.setText("Sleep          "+sleep);
                Log.e("Sleep",String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                sleep=String.valueOf(seekBar.getProgress());
                tvSleep.setText("Sleep         "+sleep);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sleep=String.valueOf(seekBar.getProgress());
                tvSleep.setText("Sleep          "+sleep);

            }
        });
        seekCal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                calories=String.valueOf(seekBar.getProgress());
                tvCal.setText("Calories          "+calories);
                Log.e("seekCal",String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                calories=String.valueOf(seekBar.getProgress());
                tvCal.setText("Calories         "+calories);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calories=String.valueOf(seekBar.getProgress());
                tvCal.setText("Calories          "+calories);

            }
        });
        seekWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                water=String.valueOf(seekBar.getProgress());
                tvWater.setText("Water          "+water);
                Log.e("Water",String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                water=String.valueOf(seekBar.getProgress());
                tvWater.setText("Water          "+water);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                water=String.valueOf(seekBar.getProgress());
                tvWater.setText("Water        "+water);

            }
        });

        setGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean status = db.insertDailyGoals(email,sleep,water,calories,steps);
                if(status){
                   finish();
                }
            }
        });
    }
}

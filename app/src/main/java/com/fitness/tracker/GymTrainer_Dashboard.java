package com.fitness.tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.fitness.tracker.gymfragment.GymExercisepageFragment;
import com.fitness.tracker.gymfragment.GymHomepageFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GymTrainer_Dashboard extends AppCompatActivity implements  SensorEventListener{
    private LinearLayout llHome, llExercise, llProfile;
    private Fragment gymfragment;
    private  boolean running = false;
//
//    SensorManager  sensorManager ;
    Sensor stepsSensor;
    String stepsValue=null;

    private SensorManager sensorManager;
    //private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_trainer__dashboard);
        llHome = findViewById(R.id.llHome);
        llExercise = findViewById(R.id.llExercise);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepsSensor =   sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState == null) {
                    onClickHome();
                }
            }
        });
        llExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(savedInstanceState == null){
                  onClickExercise();
                }
            }
        });


        onClickHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(GymTrainer_Dashboard.this, stepsSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        sensorManager.unregisterListener(GymTrainer_Dashboard.this);
    }


    private void onClickHome() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        i++;

        if (i==0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        gymfragment = new GymHomepageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Steps", stepsValue);
        gymfragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, gymfragment)
                .addToBackStack("")
                .commit();
    }

    private void onClickExercise() {
        gymfragment = new GymExercisepageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, gymfragment)
                .addToBackStack("")
                .commit();


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            int steps = (int)(sensorEvent.values[0]);
            stepsValue = "" + steps;
            onClickHome();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();

        if (i==0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }
}


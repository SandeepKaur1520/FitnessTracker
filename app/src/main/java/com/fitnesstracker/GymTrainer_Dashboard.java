package com.fitnesstracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fitnesstracker.gymfragment.GymExercisepageFragment;
import com.fitnesstracker.gymfragment.GymHomepageFragment;
import com.fitnesstracker.gymfragment.GymProfileFragment;
import com.fitnesstracker.gymfragment.RunFragment;

public class GymTrainer_Dashboard extends AppCompatActivity implements  SensorEventListener{
    private LinearLayout llHome, llExercise, llProfile,llActivity;
    private Fragment gymfragment;
    private  boolean running = false;
//
//    SensorManager  sensorManager ;
    Sensor stepsSensor;
    String stepsValue=null;
    String  email;
    private SensorManager sensorManager;
    //private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private boolean doubleBackToExitPressedOnce =false;
    String profile[]=new String[11];
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_trainer__dashboard);
        Intent inten =getIntent();
        email = inten.getStringExtra("email");
        profile = inten.getStringArrayExtra("profile");
        BottomNavigationView navigationView=findViewById(R.id.gymNavigation);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.llHome:
                        onClickHome();
                        return true;
                    case R.id.llExercise:
                        onClickExercise();
                        return true;
                    case R.id.llActivity:
                        onClickActivity();
                        return true;
                    case R.id.llProfile:
                        onProfileclick();
                        return true;
                }
                return false;
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepsSensor =   sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


 //            llHome = findViewById(R.id.llHome);
//           llExercise = findViewById(R.id.llExercise);

//        llProfile = findViewById(R.id.llProfile);
//
//        llHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (savedInstanceState == null) {
//                    onClickHome();
//                }
//            }
//        });
//        llExercise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(savedInstanceState == null){
//                  onClickExercise();
//                }
//            }
//        });
//        llProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(savedInstanceState==null)
//                {
//                  onProfileclick();
//                }
//            }
//        });
//

        onClickHome();
    }

    private void onProfileclick() {
        gymfragment = new GymProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("profile",profile);
        gymfragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containergym, gymfragment)
                .addToBackStack("")
                .commit();
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
        gymfragment = new GymHomepageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Steps", stepsValue);
        bundle.putString("email", email);
//        if(!(profile[1].isEmpty())) {
//            bundle.putString("email", profile[1]);
//        }else{
//            bundle.putString("email", email);
//        }

        gymfragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containergym, gymfragment)
                .addToBackStack("")
                .commit();
    }

    private void onClickExercise() {
        gymfragment = new GymExercisepageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containergym, gymfragment)
                .addToBackStack("")
                .commit();
    }
    private void onClickActivity() {
        gymfragment = new RunFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containergym, gymfragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running) {
            int steps = (int)(sensorEvent.values[0]);
            stepsValue = "" + steps;
            int i = getSupportFragmentManager().getBackStackEntryCount();
            if (i==0) {
                super.onBackPressed();
            }
            else {
                getSupportFragmentManager().popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE );
            }
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
            getSupportFragmentManager().popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE );
        }
        onClickHome();
        if (doubleBackToExitPressedOnce) {

            finish();
            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        CountDownTimer timer = new CountDownTimer(2000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                doubleBackToExitPressedOnce = false;
            }
        }.start();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                }else{
                    Toast.makeText(getApplicationContext(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

}


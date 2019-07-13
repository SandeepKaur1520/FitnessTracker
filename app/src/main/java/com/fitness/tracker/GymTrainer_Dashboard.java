package com.fitness.tracker;

import android.os.Bundle;

import com.fitness.tracker.fragment.CalenderFragment;
import com.fitness.tracker.gymfragment.GymExercisepageFragment;
import com.fitness.tracker.gymfragment.GymHomepageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GymTrainer_Dashboard extends AppCompatActivity {
    private LinearLayout llHome, llExercise, llProfile;
    private Fragment gymfragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_trainer__dashboard);
        llHome = findViewById(R.id.llHome);
        llExercise = findViewById(R.id.llExercise);

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
    }

    private void onClickHome() {
        gymfragment = new GymHomepageFragment();
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
}


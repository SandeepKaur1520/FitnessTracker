package com.fitness.tracker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.fitness.tracker.fragment.CalenderFragment;
import com.fitness.tracker.fragment.ChartFragment;
import com.fitness.tracker.fragment.MeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PeriodDashboard extends AppCompatActivity {
    private TextView mTextMessage;
    private Fragment fragment;
    private ImageView ivCalender;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perioddashboard);





        BottomNavigationView navigationView=findViewById(R.id.periodNavigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.llCalender:
                        onClickCalender();
                        return true;
                    case R.id.llChart:
                        onClickChart();
                        return true;
                    case R.id.llMe:
                        onMeClicked();

                        return true;
                }
                return false;
            }
        });
        onClickCalender();
    }

    private void onMeClicked() {
        fragment = new MeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commit();
    }

    private void onClickCalender() {
       /* ivCalender.setImageResource(R.mipmap.facebook);*/
        fragment = new CalenderFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commit();

    }
    private void onClickChart() {
        /* ivCalender.setImageResource(R.mipmap.facebook);*/

        fragment = new ChartFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commit();

    }


}

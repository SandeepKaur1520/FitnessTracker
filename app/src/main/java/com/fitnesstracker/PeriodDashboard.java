package com.fitnesstracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.fragment.CalenderFragment;
import com.fitnesstracker.fragment.ChartFragment;
import com.fitnesstracker.fragment.MeFragment;

public class PeriodDashboard extends AppCompatActivity {
    private TextView mTextMessage;
    private Fragment fragment;
    private ImageView ivCalender;
    boolean doubleBackToExitPressedOnce =false;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perioddashboard);
        Intent inten =getIntent();
        String profile[]=inten.getStringArrayExtra("profile");
        for (int k=0 ;k<11;k++){
            String msg = "profile  :"+k;
            Log.e(msg, profile[k]);
        }




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

    @Override
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        if (i==0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack(null , FragmentManager.POP_BACK_STACK_INCLUSIVE );
        }
        onClickCalender();
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
}

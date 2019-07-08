package com.fitness.tracker.userDataInput;

import android.net.Uri;
import android.os.Bundle;

import com.fitness.tracker.R;
import com.fitness.tracker.fragment.CalenderFragment;
import com.fitness.tracker.fragment.ChartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Tracking_info extends AppCompatActivity {
    private TextView mTextMessage;
    private LinearLayout llCalender,llChart;
    private Fragment fragment;
    private ImageView ivCalender;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_info);
        mTextMessage = findViewById(R.id.message);
        llCalender = findViewById(R.id.llCalender);
        llChart  = findViewById(R.id.llChart);

       /* ivCalender=findViewById(R.id.ivCalender);*/


        llCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(savedInstanceState==null){
                   onClickCalender();
               }
            }
        });
        llChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(savedInstanceState==null){
                    onClickChart();
                }
            }
        });

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

package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Period_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.period_info);

        Button PeriodInfoNext;
        PeriodInfoNext = findViewById(R.id.bPeriodinfoNext);
        PeriodInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Period_info.this, PeriodDashboard.class);
                startActivity(intent);
            }
        });
    }
}

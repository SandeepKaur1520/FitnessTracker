package com.fitnesstracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class Period_info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button  StartPeriodDayDialogbtn ;
    String  date ;
    int StartPeriodDay ;
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


        StartPeriodDayDialogbtn = findViewById(R.id.StartPeriodDayDialog);
        StartPeriodDayDialogbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                        int startYear=2019,starthMonth=07,startDay=18;
                        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                                Period_info.this, (DatePickerDialog.OnDateSetListener) Period_info.this, startYear, starthMonth, startDay);
                        datePickerDialog.show();
                    }
                });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = ("Date: " + dayOfMonth + " Month: " + month + " Year: " + year);
        date =(dayOfMonth+"/"+month+"/"+year);
        Toast.makeText(Period_info.this,date, Toast.LENGTH_SHORT).show();
        StartPeriodDayDialogbtn.setText("  Date of Birth : "+date);

    }
}

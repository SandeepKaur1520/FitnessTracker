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

import java.util.Calendar;

public class Period_info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button StartPeriodDayDialogbtn, periodlengthbtn , setperiodlength;
    String date;
    int StartPeriodDay;
    Calendar calendar = Calendar.getInstance();
    int startYear = calendar.get(Calendar.YEAR);
    int startMonth = calendar.get(Calendar.MONTH) + 1;
    int startDay = calendar.get(Calendar.DATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.period_info);

        Button PeriodInfoNext;
        PeriodInfoNext = findViewById(R.id.bPeriodinfoNext);
        PeriodInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Period_info.this, PeriodDashboard.class);
                startActivity(intent);
            }
        });


        StartPeriodDayDialogbtn = findViewById(R.id.StartPeriodDayDialog);
        periodlengthbtn = findViewById(R.id.PeriodLengthDialog);
        StartPeriodDayDialogbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Period_info.this, (DatePickerDialog.OnDateSetListener) Period_info.this, startYear, startMonth, startDay);
                datePickerDialog.show();
            }
        });
/*
        periodlengthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Period_info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_height_picker);
                dialog.setCancelable(false);
                setperiodlength =dialog.findViewById(R.id.SetPeriodLength);
               *//* setperiodlength.setOnClickListener(new View.OnClickListener()*//*
                {
                    @Override
                    public void onClick(View v) {

                        periodlengthbtn.setText("period days : "++"cm");
                        dialog.dismiss();
                        isHeight=true;
                    }
               });
            }
        });*/

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        startDay = dayOfMonth;
        startMonth = month;
        startYear = year;
        date = ("Date: " + dayOfMonth + " Month: " + month + " Year: " + year);
        date = (dayOfMonth + "/" + month + "/" + year);
        Toast.makeText(Period_info.this, date, Toast.LENGTH_SHORT).show();
        StartPeriodDayDialogbtn.setText("  Date of Birth : " + date);

    }

}

package com.fitnesstracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

import java.util.Calendar;

public class Period_info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String date;
    Button StartPeriodDayDialogbtn;
    Calendar calendar = Calendar.getInstance();
    int startYear = calendar.get(Calendar.YEAR);
    int startMonth = calendar.get(Calendar.MONTH) + 1;
    int startDay = calendar.get(Calendar.DATE);
    String lastStartDate,periodLength,cycleLength,email;
    boolean isPeriodSelected=false;
    boolean isCycleSelected =false;
    boolean isLastDateSelected =false;
    boolean isLastDateVaild=false ;
    DatabaseHelper db = new DatabaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.period_info);

        Intent obj = getIntent();
        email = obj.getStringExtra("periodInfoEmail");
        Log.e("Email in oncreate ",email);
        periodLength = "1";
        cycleLength = "15";
        Button StartPeriodDayDialogbtn = findViewById(R.id.StartPeriodDayDialog);

        StartPeriodDayDialogbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Period_info.this, (DatePickerDialog.OnDateSetListener) Period_info.this, startYear, startMonth, startDay);
                datePickerDialog.show();
            }
        });
        final Button periodlengthbtn =findViewById(R.id.PeriodLengthDialog);
        periodlengthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Period_info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_periodlength);
                dialog.setCancelable(false);
                

                final NumberPicker periodLengthNP = dialog.findViewById(R.id.periodLengthNP);
                periodLengthNP.setMaxValue(15);
                periodLengthNP.setMinValue(1);
                periodLength="1";
                periodLengthNP.setWrapSelectorWheel(true);
                periodLengthNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        periodLength=String.valueOf(picker.getValue());
                    }
                });




                final Button setperiodlength = dialog.findViewById(R.id.setPeriodLength);
                setperiodlength.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        isPeriodSelected=true;
                        periodlengthbtn.setText("Period Length : "+periodLength);
                        dialog.dismiss();

                    }
               });

                dialog.show();
                
            }
        });
        final Button cycleLengthbtn=findViewById(R.id.CycleLengthDialog);
        cycleLengthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Period_info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_cyclelength);
                dialog.setCancelable(false);

                cycleLength="15";
                final NumberPicker cycleLengthNP = dialog.findViewById(R.id.cycleLengthNP);
                cycleLengthNP.setMaxValue(50);
                cycleLengthNP.setMinValue(15);
                cycleLengthNP.setWrapSelectorWheel(true);
                cycleLengthNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        cycleLength=String.valueOf(picker.getValue());
                    }
                });

                Button setCycleLength=dialog.findViewById(R.id.setCycleLength);
                setCycleLength.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cycleLengthbtn.setText("Cycle Length : "+cycleLength);
                        dialog.dismiss();
                        isCycleSelected=true;
                    }
                });

                dialog.show();
            }
        });

        final Button PeriodInfoNext;
        PeriodInfoNext = findViewById(R.id.bPeriodinfoNext);
        PeriodInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCycleSelected&&isPeriodSelected&&isLastDateSelected&&isLastDateVaild) {
                     Boolean status = db.insertPeriodInfo(email, periodLength, cycleLength, lastStartDate);
                    if (status) {

                        Intent intent = new Intent(Period_info.this, PeriodDashboard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Period_info.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        startDay = dayOfMonth;
        startMonth = month;
        startYear = year;
        date = ("Date: " + dayOfMonth + " Month: " + month + " Year: " + year);
        date = (dayOfMonth + "/" + month + "/" + year);
        lastStartDate=date;
        isLastDateSelected=true;
        Toast.makeText(Period_info.this, date, Toast.LENGTH_SHORT).show();
        StartPeriodDayDialogbtn =findViewById(R.id.StartPeriodDayDialog);
        StartPeriodDayDialogbtn.setText(" Last Start Date : " + date);
        if(true){
            isLastDateVaild=true;
        }else{
            isLastDateVaild=false;
        }
    }

}

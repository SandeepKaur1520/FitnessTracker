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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Period_info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String date;
    Button StartPeriodDayDialogbtn;
    Calendar calendar = Calendar.getInstance();
    int startYear = calendar.get(Calendar.YEAR);
    int startMonth = calendar.get(Calendar.MONTH) ;
    int startDay = calendar.get(Calendar.DATE);
    public static  String lastEndDate,lastStartDate,periodLength,cycleLength,email;
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
        email = obj.getStringExtra("email");
        Log.e("Email in oncreate ",email);
        periodLength = "1";
        cycleLength = "15";
        Button StartPeriodDayDialogbtn = findViewById(R.id.StartPeriodDayDialog);

        StartPeriodDayDialogbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Period_info.this, (DatePickerDialog.OnDateSetListener) Period_info.this, startYear, startMonth, startDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
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
                    int length   = Integer.parseInt(periodLength);
                    lastEndDate =calEndDate(length);
                    String periodStatus = "end";
                    db.insertPeriodInfo(email, periodLength, cycleLength, lastStartDate,lastEndDate,periodStatus);
                    Boolean status = db.insertPeriodInfo(email, periodLength, cycleLength, lastStartDate,lastEndDate,periodStatus);
                    if (status) {
                        Intent intent = new Intent(Period_info.this, PeriodDashboard.class);
                        intent.putExtra("email",email);
//                        Toast.makeText(Period_info.this,"Data Saved",Toast.LENGTH_LONG).show();
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
        int x=month+1;
        startDay = dayOfMonth;
        startMonth = month;
        startYear = year;
        date = ("Date: " + dayOfMonth + " Month: " + x + " Year: " + year);
        date = (dayOfMonth + "/" + x + "/" + year);
        lastStartDate=date;
        isLastDateSelected=true;
        Toast.makeText(Period_info.this, date, Toast.LENGTH_SHORT).show();
        StartPeriodDayDialogbtn =findViewById(R.id.StartPeriodDayDialog);
        StartPeriodDayDialogbtn.setText(" Last Start Date : " + date);
        Calendar cStart = Calendar.getInstance();
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(Calendar.getInstance().getTime());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date StartDate = sdf.parse(lastStartDate);

            /**Calculating lastendDate*/
            cStart.setTime(StartDate);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(cStart.before(cEnd)){
            isLastDateVaild=true;
        }else{
            Toast.makeText(Period_info.this,"Last Period Date can't Be in Future",Toast.LENGTH_LONG).show();
            isLastDateVaild=false;
        }
    }

    public String calEndDate(int Periodlength){
        String LastEndDate =null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date StartDate = sdf.parse(lastStartDate);
            Calendar c = Calendar.getInstance();
            /**Calculating lastendDate*/
            c.setTime(StartDate);
            Log.e("Last Start Date", StartDate.toString()+"   +   "+Periodlength);
//            while (Periodlength>1) {
//                c.add(Calendar.DAY_OF_MONTH, 1);
//                Periodlength--;
//            }
            c.add(Calendar.DAY_OF_MONTH,Periodlength);
            LastEndDate = sdf.format(c.getTime());
            Log.e("LastEnd Into Database",LastEndDate);

        }catch (Exception e){
            e.printStackTrace();

        }return LastEndDate;
    }

}

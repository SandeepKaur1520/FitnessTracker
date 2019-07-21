package com.fitnesstracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Health_Info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button nextBtn, heightBtn ,setHeightBtn , weightBtn , setWeightBtn , DOBbtn , setDOB;
    double weight,height,x=0,x1=0;
    String date,email;
    boolean isHeight=false;
    boolean isWeight=false;
    boolean isDOB=false;
    boolean isDOBValid=false;
    Calendar calendar = Calendar.getInstance();
    int startYear =calendar.get(Calendar.YEAR);
    int startMonth = calendar.get(Calendar.MONTH)+1;
    int startDay= calendar.get(Calendar.DATE);
    int currentYear=startYear;
    DatabaseHelper db = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health__info);
        Intent intent =getIntent();
        email =intent.getStringExtra("email");
        heightBtn = findViewById(R.id.HeightDialog);
        heightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = 120;

                final Dialog dialog = new Dialog(Health_Info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_height_picker);
                dialog.setCancelable(false);
                setHeightBtn =dialog.findViewById(R.id.setheight);
                setHeightBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        double y = 0.1*x;
                        height = height + y;
                        heightBtn.setText("  Height : "+height+"cm");
                        dialog.dismiss();
                        isHeight=true;
                        heightBtn.setTextColor(getResources().getColor(R.color.whiteText));
                    }
                });

                final NumberPicker cmNP = dialog.findViewById(R.id.cmNP);
                cmNP.setMaxValue(250);
                cmNP.setMinValue(120);
                cmNP.setWrapSelectorWheel(true);
                cmNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        height = numberPicker.getValue();
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));
                    }
                });
                final NumberPicker mmNP = dialog.findViewById(R.id.mmNP);
                mmNP.setMaxValue(9);
                mmNP.setMinValue(0);
                mmNP.setWrapSelectorWheel(true);

                mmNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        x = (double) numberPicker.getValue();
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));


                    }
                });

                dialog.show();
            }
        });

        weightBtn = findViewById(R.id.weightDialog);
        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               weight=35;

                final Dialog dialog = new Dialog(Health_Info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_weight_picker);
                dialog.setCancelable(false);
                setWeightBtn=dialog.findViewById(R.id.setWeight);
                setWeightBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        double y = 0.1*x1;
                        weight = weight + y;
                        weightBtn.setText("  Weight : "+weight + " kg");
                        dialog.dismiss();
                        isWeight=true;
                        weightBtn.setTextColor(getResources().getColor(R.color.whiteText));
                    }
                });

                final NumberPicker kgNP = dialog.findViewById(R.id.kgNP);
                kgNP.setMaxValue(110);
                kgNP.setMinValue(35);
                kgNP.setWrapSelectorWheel(true);

                kgNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        weight = numberPicker.getValue();
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));

                    }
                });

                final NumberPicker gNP = dialog.findViewById(R.id.gNP);
                gNP.setMaxValue(9);
                gNP.setMinValue(0);
                gNP.setWrapSelectorWheel(true);

                gNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        x1 = (double) numberPicker.getValue();

                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));



                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));

                    }
                });

                dialog.show();

            }
        });

        DOBbtn = findViewById(R.id.DateOfBirthDialog);
        DOBbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                Log.e("getYear  = " ,String.valueOf(startYear));
                Log.e("getMonth = ",String.valueOf(startMonth));
                Log.e("getDate = ",String.valueOf(startDay));


                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Health_Info.this, (DatePickerDialog.OnDateSetListener) Health_Info.this, startYear, startMonth, startDay);
                datePickerDialog.show();
            }
        });

        nextBtn = findViewById(R.id.bHelathInfoNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHeight){//when height is t select
                    heightBtn.setTextColor(getResources().getColor(R.color.whiteText));
                }else{
                    Toast.makeText(Health_Info.this, "Please Select these fields", Toast.LENGTH_LONG).show();
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    heightBtn.setTextColor(getResources().getColor(R.color.Red));
                }
                if(isWeight){//when weight is select
                    weightBtn.setTextColor(getResources().getColor(R.color.whiteText));
                }else{
                    Toast.makeText(Health_Info.this, "Please Select these fields", Toast.LENGTH_LONG).show();
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    weightBtn.setTextColor(getResources().getColor(R.color.Red));
                }
                if(isDOB){//when DOB is select
                    DOBbtn.setTextColor(getResources().getColor(R.color.whiteText));
                }else{
                    Toast.makeText(Health_Info.this, "Please Select these fields", Toast.LENGTH_LONG).show();
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    DOBbtn.setTextColor(getResources().getColor(R.color.Red));

                }
                if(isDOBValid){//when DOB is select
                    DOBbtn.setTextColor(getResources().getColor(R.color.whiteText));
                }else{
                    Toast.makeText(Health_Info.this, "Please Select a Vaild DOB", Toast.LENGTH_LONG).show();
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(300);
                    DOBbtn.setTextColor(getResources().getColor(R.color.Red));


                }
                if(isWeight&&isDOB&&isHeight&&isDOBValid) {
                    boolean status = db.updateHealthInfo(email, height, weight, date);
                    if (status) {
                        Toast.makeText(Health_Info.this, "Your Data Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Health_Info.this, Selected_Field.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Health_Info.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = ("Date: " + dayOfMonth +" Month: " + month +" Year: "+year);
        date =(dayOfMonth+"/"+(month+1)+"/"+year);
        Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
        DOBbtn.setText("  Date of Birth : "+date);
        isDOB=true;
        DOBbtn.setTextColor(getResources().getColor(R.color.whiteText));
        if((currentYear - year) > 5){
            isDOBValid=true;
            startYear=year;
            startDay=dayOfMonth;
            startMonth=month;
        }else{
            isDOBValid=false;
            DOBbtn.setTextColor(getResources().getColor(R.color.Red));
            Toast.makeText(Health_Info.this, "Please Select a Vaild DOB", Toast.LENGTH_LONG).show();
        }

    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        String date = ("Date:" +i2 +"Month: " +  i1 +"Year: "+i);
//        Storage[1]=date;
//        Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
//    }

}






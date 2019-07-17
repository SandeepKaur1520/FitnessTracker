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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

public class Health_Info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button nextBtn, heightBtn ,setHeightBtn , weightBtn , setWeightBtn , DOBbtn , setDOB;
    double weight,height;
    String date,email;
    DatabaseHelper db = new DatabaseHelper(this);
    final String[] Storage = {"","","","","","","","",""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health__info);

        heightBtn = findViewById(R.id.HeightDialog);
        heightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Health_Info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_height_picker);
                dialog.setCancelable(false);
                setHeightBtn =dialog.findViewById(R.id.setheight);
                setHeightBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        heightBtn.setText("Height : "+height+"cm");
                        dialog.dismiss();
                    }
                });

                final NumberPicker cmNP = dialog.findViewById(R.id.cmNP);
                cmNP.setMaxValue(250);
                cmNP.setMinValue(120);
                cmNP.setWrapSelectorWheel(false);
                cmNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        height = numberPicker.getValue();
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));

                    }
                });
                final NumberPicker mmNP = dialog.findViewById(R.id.mmNP);
                mmNP.setMaxValue(100);
                mmNP.setMinValue(0);
                mmNP.setWrapSelectorWheel(false);

                mmNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        double x = (double) numberPicker.getValue();
                        double y = 0.01*x;
                        height = height + y;
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));
                        Log.e("Height : ",String.valueOf(height));

                    }
                });

                dialog.show();
            }
        });

        weightBtn = findViewById(R.id.weightDialog);
        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Health_Info.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_weight_picker);
                dialog.setCancelable(false);
                setWeightBtn=dialog.findViewById(R.id.setWeight);
                setWeightBtn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        weightBtn.setText("Weight : "+weight + " kg");
                        dialog.dismiss();
                    }
                });

                final NumberPicker kgNP = dialog.findViewById(R.id.kgNP);
                kgNP.setMaxValue(110);
                kgNP.setMinValue(35);
                kgNP.setWrapSelectorWheel(false);

                kgNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        weight = numberPicker.getValue();
                        Log.e("onValueChange: ", String.valueOf((numberPicker.getValue())));

                    }
                });

                final NumberPicker gNP = dialog.findViewById(R.id.gNP);
                gNP.setMaxValue(100);
                gNP.setMinValue(0);
                gNP.setWrapSelectorWheel(false);

                gNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        double x = (double) numberPicker.getValue();
                        double y = 0.01*x;
                        weight = weight + y;
                        Log.e("Weight : ",String.valueOf(weight));



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
//                final Dialog dialog = new Dialog(Health_Info.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_dob_picker);
//                dialog.setCancelable(false);
//                setDOB=dialog.findViewById(R.id.setDOBbtn);
//                setDOB.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v) {
//                        DOBbtn.setText("Date of Birth : "+date);
//                        dialog.dismiss();
//                    }
//                });
//                DatePicker picker = findViewById(R.id.Calenderview);
//                picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//                    @Override
//                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        date = ("Date: " + dayOfMonth +" Month: " + monthOfYear +" Year: "+year);
//                                date =(dayOfMonth+"/"+monthOfYear+"/"+year);
//                                Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
//                    }
//                });
//               CalendarView calendarView =findViewById(R.id.Calenderview);
//
//                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//                    @Override
//                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                                date = ("Date: " + dayOfMonth +" Month: " + month +" Year: "+year);
//                                date =(dayOfMonth+"/"+month+"/"+year);
//                                Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
//                    }
//                });
                int startYear=2019,starthMonth=07,startDay=18;
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Health_Info.this, (DatePickerDialog.OnDateSetListener) Health_Info.this, startYear, starthMonth, startDay);
                datePickerDialog.show();
//                dialog.show();

            }
        });

        nextBtn = findViewById(R.id.bHelathInfoNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateHealthInfo(email,height,weight,date);
                Intent intent = new Intent(Health_Info.this, Selected_Field.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = ("Date: " + dayOfMonth +" Month: " + month +" Year: "+year);
        date =(dayOfMonth+"/"+month+"/"+year);
        Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
        DOBbtn.setText("Date of Birth : "+date);
    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        String date = ("Date:" +i2 +"Month: " +  i1 +"Year: "+i);
//        Storage[1]=date;
//        Toast.makeText(Health_Info.this,date, Toast.LENGTH_SHORT).show();
//    }

}






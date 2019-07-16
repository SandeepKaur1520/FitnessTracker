package com.fitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Selected_Field extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected__field);

        final int[] flag = {0};//if flag is 0 then nothing is selected if it is 1 then GymTrainer is Selelcted if it is 2 then peroiod tracker is selected

        final LinearLayout llGymTrainer = findViewById(R.id.llGymTraner);
        final LinearLayout llPeriodTracker = findViewById(R.id.llPeriodTracker);
        RadioGroup RGymPeriod = findViewById(R.id.RGymPeriod);
        Button bSelectedFieldNext = findViewById(R.id.bSelectedFiledNext);
        final TextView selectField = findViewById(R.id.tvselectField);
        final RadioButton rbtnGym = findViewById(R.id.rbtngymTranier);
        final RadioButton rbtnPeriod = findViewById(R.id.rbtnperiodTracker);
        llGymTrainer.setVisibility(View.GONE);
        llPeriodTracker.setVisibility(View.GONE);


        RGymPeriod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtngymTranier){
                    rbtnGym.setTextColor(getResources().getColor(R.color.colorAccent));
                    rbtnPeriod.setTextColor(getResources().getColor(R.color.colorAccent));
                    selectField.setTextColor(getResources().getColor(R.color.colorAccent));
                    llGymTrainer.setVisibility(View.VISIBLE);
                    llPeriodTracker.setVisibility(View.GONE);
                    flag[0] = 1;

                }
                else if(checkedId == R.id.rbtnperiodTracker){
                    rbtnGym.setTextColor(getResources().getColor(R.color.Pink));
                    rbtnPeriod.setTextColor(getResources().getColor(R.color.Pink));
                    selectField.setTextColor(getResources().getColor(R.color.Pink));

                    llGymTrainer.setVisibility(View.GONE);
                    llPeriodTracker.setVisibility(View.VISIBLE);
                    flag[0] = 2;


                }
            }
        });

        bSelectedFieldNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[0] == 0){
                    Toast.makeText(Selected_Field.this,"Please Select any field",Toast.LENGTH_LONG).show();
                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                }
                else if(flag[0] == 1){
                    Intent intent =new Intent(Selected_Field.this, BodyInfo.class);
                    startActivity(intent);
                }
                else if(flag[0] == 2){
                    Intent intent =new Intent(Selected_Field.this, Period_info.class);
                    startActivity(intent);
                }
            }
        });

    }
}

package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fitnesstracker.database.DatabaseHelper;

public class BodyInfo extends AppCompatActivity {
    String email;
    boolean skinnyArms,beerBelly,thinLegs,weakChest;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_info);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        ToggleButton arms = findViewById(R.id.toggleSkinnyArms);
        ToggleButton belly = findViewById(R.id.toggleBeerBelly);
        ToggleButton legs = findViewById(R.id.toggleThinLegs);
        ToggleButton chest = findViewById(R.id.toggleWeakChest);

        arms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    skinnyArms=true;
                else
                    skinnyArms=false;
            }
        });

        belly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    beerBelly=true;
                else
                    beerBelly=false;
            }
        });

        legs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    thinLegs=true;
                else
                    thinLegs=false;
            }
        });

        chest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    weakChest=true;
                else
                    weakChest=false;
            }
        });

        Button btnNext =(Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(skinnyArms||weakChest||thinLegs||beerBelly) {
                    Boolean status = db.updateBodyinfo(email,skinnyArms,weakChest,beerBelly,thinLegs);
                    if(status) {
                        Toast.makeText(BodyInfo.this,"Your Data Saved",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BodyInfo.this, BodyActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }else
                        Toast.makeText(BodyInfo.this,"Somethong Went Wrong",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(BodyInfo.this,"Please select at least one Option",Toast.LENGTH_LONG).show();
                    CountDownTimer countDownTimer =new CountDownTimer(2000,20) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(BodyInfo.this,"Please select at least one Option",Toast.LENGTH_LONG).show();
                        }
                    };
                }
            }
        });
    }
}

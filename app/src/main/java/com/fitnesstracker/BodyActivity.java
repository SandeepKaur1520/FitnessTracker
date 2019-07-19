package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

public class BodyActivity extends AppCompatActivity {
    String email,BodyActiveness;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodyactivity);
        Intent intent1 = getIntent();
        email = intent1.getStringExtra("email");
        Button nextBtn = findViewById(R.id.bNextBodyActivity);
        RadioGroup RGbodyActiveness = findViewById(R.id.RGbodyActiveness);
        RGbodyActiveness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtndWorkoutHero) {
                    BodyActiveness = "Workout Hero";
                } else if (checkedId == R.id.rbtnlightMode) {
                    BodyActiveness = "Light Mode";
                } else if (checkedId == R.id.rbtnNewOne) {
                    BodyActiveness = "New One";
                }

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean status = false;
                status = db.updateBodyActiveness(email, BodyActiveness);
                if (status) {
                    Intent intent = new Intent(BodyActivity.this, GymTrainer_Dashboard.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    Toast.makeText(BodyActivity.this,"Your Data Saved",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(BodyActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
                /*else{
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
*/
            }
        });
    }
}

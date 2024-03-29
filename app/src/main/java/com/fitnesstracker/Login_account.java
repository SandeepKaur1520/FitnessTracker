package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;


public class Login_account extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_account);

        final EditText etEmail=findViewById(R.id.etEmailId);
        final EditText etPass=findViewById(R.id.etPassword);


        final DatabaseHelper db = new DatabaseHelper(this);
        Button loginToHealth = findViewById(R.id.bLoginToHealth);
        loginToHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();

                boolean status = db.verifyUser(email,password);
                    if(status) {
                        String profile[]=db.getUserProfile(email);
                        if(profile[9].contentEquals("GymTrainer")) {
                            Intent intent = new Intent(Login_account.this, GymTrainer_Dashboard.class);
                            intent.putExtra("email", email);
                            intent.putExtra("profile",profile);
                            startActivity(intent);
                        }else if(profile[9].contentEquals("PeriodTracker")){
                            Intent intent = new Intent(Login_account.this, PeriodDashboard.class);
                            intent.putExtra("email", email);
                            intent.putExtra("profile",profile);
                            startActivity(intent);
                        }else if(profile==null){
                            Toast.makeText(Login_account.this,"Create Anccount Again",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Login_account.this,"Wrong Credentials",Toast.LENGTH_LONG).show();
                    }


            }
        });
        Button SignUpPage;
        SignUpPage = findViewById(R.id.bSignupAgain);
        SignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login_account.this, Personal_info.class);
                startActivity(intent);
            }
        });
    }
}

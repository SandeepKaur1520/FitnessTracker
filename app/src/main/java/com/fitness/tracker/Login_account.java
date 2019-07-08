package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_account);

        Button loginToHealth;
        loginToHealth = findViewById(R.id.bLoginToHealth);
        loginToHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data (details about the profile of user ) fetched from  database

                // service is a a string whose values id fetched from dtabases atribute
                //if(service.equals('Gym'))
                //{Intent intent =new Intent(Login_account.this,Gym_dashboard.class);
               /*
                else if(service.equals("Period Tracker"))

                */
                Intent intent =new Intent(Login_account.this, Health_Info.class);
                startActivity(intent);
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

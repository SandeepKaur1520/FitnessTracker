package com.fitness.tracker;

import com.fitness.tracker.database.*;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

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
                        Intent intent = new Intent(Login_account.this, Health_Info.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login_account.this,"You need to work hard ",Toast.LENGTH_LONG).show();
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

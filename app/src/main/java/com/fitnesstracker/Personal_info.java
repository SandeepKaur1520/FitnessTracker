package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

public class Personal_info extends AppCompatActivity {

    EditText  firstName,lastName,email,pass, cnfPass ;;
    Spinner gender;
    TextView invalidEmailMessage,invalidConfirmPass;
    Boolean isEmailValid,isConfirmPassValid;
    String Gender, FirstName, LastName, eMail, Pass, CnfPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        firstName = findViewById(R.id.etfirst_name);
        lastName =findViewById(R.id.etlast_name);
        gender  =findViewById(R.id.sGender);
        email = findViewById(R.id.etemail);
        pass = findViewById(R.id.etPass);
        cnfPass = findViewById(R.id.etconfirm_password);
        invalidEmailMessage =findViewById(R.id.tvInvalidEmailMessage);
        Gender = "Male";



        final DatabaseHelper db =new DatabaseHelper(this);


        gender.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String x = gender.getSelectedItem().toString();
                if(x.equals("Male")){
                    Gender = "Male";
                }
                else if(x.equals("Female")){
                    Gender = "Female";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((!TextUtils.isEmpty(s)) && (Patterns.EMAIL_ADDRESS.matcher(s).matches()) ){//when email is valid
                    invalidEmailMessage.setVisibility(View.GONE);
                    isEmailValid=true;
                }else{
                    isEmailValid=false;
                    invalidEmailMessage.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cnfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pass.getText().toString();
//                if((!TextUtils.isEmpty(s)) && (password.matches(s))){
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button next = findViewById(R.id.bNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gender = gender.getSelectedItem().toString();
                FirstName = firstName.getText().toString();
                LastName = lastName.getText().toString();
                eMail = email.getText().toString();
                Pass = pass.getText().toString();
                CnfPass = cnfPass.getText().toString();
                if((isEmailValid) ) {
                    invalidEmailMessage.setVisibility(View.GONE);

                    boolean status = false;
                    try {
                        status = db.createUser(eMail, Pass, FirstName, LastName, Gender);
                    } catch (Exception e) {
                        Toast.makeText(Personal_info.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                    }

                    if (status) {
                        Toast.makeText(Personal_info.this, "Data Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Personal_info.this, Health_Info.class);
                        intent.putExtra("email", eMail);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Personal_info.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    invalidEmailMessage.setVisibility(View.VISIBLE);
                    Toast.makeText(Personal_info.this,"Please Enter a valid email",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

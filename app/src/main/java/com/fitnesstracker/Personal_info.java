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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseHelper;

public class Personal_info extends AppCompatActivity {

    EditText  firstName,lastName,email,pass, cnfPass ;;
    Spinner gender;
    TextView invalidEmailMessage,invalidConfirmPass,invalidFirstName,invalidPassword;
    Boolean isEmailValid=false,isConfirmPassValid=false,isPasswordValid=false,isFirstNameEntered=false,isPasswordEntered=false,isConfirmPassEntered=false,EmailAldreadyExist=false;
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
        invalidEmailMessage = findViewById(R.id.tvInvalidEmailMessage);
        invalidFirstName = findViewById(R.id.tvInvalidFirstMessage);
        invalidPassword = findViewById(R.id.tvPassEmptyMessage);
        invalidConfirmPass = findViewById(R.id.tvInvalidConfirm);
        Gender = "Male";
        invalidFirstName.setVisibility(View.GONE);


        final DatabaseHelper db =new DatabaseHelper(this);

        firstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String firtname= firstName.getText().toString();
               if(firtname.isEmpty()){
                   isFirstNameEntered=false;
               }else{
                   isFirstNameEntered=true;
               }

            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!(TextUtils.isEmpty(charSequence))){
                    invalidFirstName.setVisibility(View.GONE);
                    isFirstNameEntered=true;
                }else{
                    invalidFirstName.setVisibility(View.VISIBLE);
                    isFirstNameEntered=false;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((!TextUtils.isEmpty(s)) && (Patterns.EMAIL_ADDRESS.matcher(s).matches()) ){//when email is valid
                    invalidEmailMessage.setVisibility(View.GONE);
                    isEmailValid=true;
                }else{isEmailValid=false;
                    invalidEmailMessage.setVisibility(View.VISIBLE);}}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pass= pass.getText().toString();
                if(Pass.isEmpty()){
                    isPasswordEntered=false;
                }else{
                    isPasswordEntered=true;
                }

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Pass = pass.getText().toString();
                Log.e("Count ","i = "+ i+"i1 = "+i1+"i2 = "+i2);
                Log.e("Count ","Pass "+Pass.length());
                if(Pass.length()<6 && Pass.length()>0){
                    invalidPassword.setText("Password must be atleast of 6 Character");
                    invalidPassword.setVisibility(View.VISIBLE);
                    isPasswordValid = false;
                    isPasswordEntered = false;
                }
                else if(Pass.length() == 0){
                    invalidPassword.setText("Password is Mandatory");
                    invalidPassword.setVisibility(View.VISIBLE);
                    isPasswordValid = false;
                    isPasswordEntered = false;
                }else{
                    invalidPassword.setText("Password must be atleast of 6 Character");
                    isPasswordEntered = true;
                    invalidPassword.setVisibility(View.GONE);
                    isPasswordValid = true;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        cnfPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pass=cnfPass.getText().toString();
                if(Pass.isEmpty()){
                    isConfirmPassEntered=false;
                }else{
                    isConfirmPassEntered=true;
                }

            }
        });

        cnfPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pass.getText().toString();
                String cnfPassword = cnfPass.getText().toString();
                if((!TextUtils.isEmpty(s)) && (password.equals(cnfPassword))){
                    isConfirmPassValid = true;
                    isConfirmPassEntered = true;
                    invalidConfirmPass.setVisibility(View.GONE);
                }else{
                    isConfirmPassValid = false;
                    isConfirmPassEntered = false;
                    invalidConfirmPass.setVisibility(View.VISIBLE);
                }
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
                if(isConfirmPassValid){
                    invalidConfirmPass.setVisibility(View.GONE);
                }else{
                    invalidConfirmPass.setVisibility(View.VISIBLE);
                }
                if(isPasswordValid){
                    invalidPassword.setVisibility(View.GONE);
                }else{
                    invalidPassword.setVisibility(View.VISIBLE);
                }
                if(isPasswordEntered){
                    invalidPassword.setVisibility(View.GONE);
                }else {
                    invalidPassword.setVisibility(View.VISIBLE);
                }
                if(isConfirmPassEntered){
                    invalidConfirmPass.setVisibility(View.GONE);
                }else{
                    invalidConfirmPass.setVisibility(View.VISIBLE);
                }
                if(isFirstNameEntered) {
                    invalidFirstName.setVisibility(View.GONE);}
                else {
                    invalidFirstName.setVisibility(View.VISIBLE);}
                if ((isEmailValid)) {
                    invalidEmailMessage.setVisibility(View.GONE);
                }else {
                    invalidEmailMessage.setVisibility(View.VISIBLE);
                }
                if(isConfirmPassValid&&isPasswordValid&&isConfirmPassEntered&&isFirstNameEntered&&isEmailValid&&isPasswordEntered){
                    boolean status = false;
                    try {
                        status = db.createUser(eMail, Pass, FirstName, LastName, Gender);
                        EmailAldreadyExist = false;
                    } catch (Exception e) {
                        Toast.makeText(Personal_info.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                        EmailAldreadyExist = true;
                    }
                    if(EmailAldreadyExist){
                        Toast.makeText(Personal_info.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                    }
                    if (status && !EmailAldreadyExist) {
                        Toast.makeText(Personal_info.this, "Data Saved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Personal_info.this, Health_Info.class);
                        intent.putExtra("email", eMail);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Personal_info.this, "Email Already Exists", Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(Personal_info.this,"Red Fields As Mandatory to Proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

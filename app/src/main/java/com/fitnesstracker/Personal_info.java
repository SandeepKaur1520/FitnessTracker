package com.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnesstracker.database.DatabaseOfUser;

public class Personal_info extends AppCompatActivity {

    EditText  firstName ;
    EditText lastName;
    Spinner gender;
    EditText email ;
    EditText pass ;
    EditText cnfPass ;

    String Gender;
    String FirstName ;
    String LastName;
    String eMail;
    String Pass;
    String CnfPass;


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




        final DatabaseOfUser db =new DatabaseOfUser(this);


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
                else if(x.equals("Other")){   /*gender.getSelectedItem().toString()*/
                    Gender = "Other";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                boolean status = false;
                try{
                    status = db.createUser(eMail, Pass, FirstName, LastName, Gender);
                }catch (Exception e){
                    Toast.makeText(Personal_info.this,"Email Already Exists",Toast.LENGTH_LONG).show();
                }

                if(status) {
                    Toast.makeText(Personal_info.this,"Data Saved",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Personal_info.this, Health_Info.class);
                    intent.putExtra("email",eMail);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Personal_info.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

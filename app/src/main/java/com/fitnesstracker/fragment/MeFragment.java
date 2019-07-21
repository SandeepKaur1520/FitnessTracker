package com.fitnesstracker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DatabaseHelper;

public class MeFragment extends Fragment {
    String email,profile[]= new String[11];
    Context context;
    DatabaseHelper db ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.mefragment,container,false);
        email = getArguments().getString("email");
        profile =db.getUserProfile(email);
        int cyclelength = db.getAvgCycleLength(email);
        int periodlength = db.getAvgPeriodLength(email);

        EditText nameET = view.findViewById(R.id.etName);
        EditText dobET = view.findViewById(R.id.etDOB);
        EditText emailET = view.findViewById(R.id.etEMail);
        EditText heightET = view.findViewById(R.id.etHeight);
        EditText weigthET = view.findViewById(R.id.etWeigth);
        EditText passET = view.findViewById(R.id.passwordET);
        EditText genderET = view.findViewById(R.id.genderET);

        TextView cycleDisplay = view.findViewById(R.id.cycleLength_Display);
        TextView periodDisplay = view.findViewById(R.id.periodLength_Display);
        cycleDisplay.setText(cyclelength+" Days");
        periodDisplay.setText(periodlength+" Days");

        final EditText passwordET = view.findViewById(R.id.passwordET);
        final ToggleButton eyeButton = view.findViewById(R.id.eyeButton);
        eyeButton.setChecked(false);

        eyeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        nameET.setText(profile[3]+" "+profile[4]);
        dobET.setText(profile[8]);
        emailET.setText(profile[1]);
        heightET.setText(profile[6]+ "cm");
        weigthET.setText(profile[7]+" kg");
        passET.setText(profile[2]);
        if(profile[5].contentEquals("Male")){
            genderET.setText("Male");
        }else if(profile[5].contentEquals("Female")){
            genderET.setText("Female");
        }

        return view;
    }

}

package com.fitnesstracker.gymfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fitnesstracker.R;


public class GymProfileFragment extends Fragment {
    String profile[]= new String[11];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.profile_fragment,container,false);

        profile = getArguments().getStringArray("profile");
        EditText nameET = view.findViewById(R.id.etNameGym);
        EditText dobET = view.findViewById(R.id.etDOBGym);
        EditText emailET = view.findViewById(R.id.etEMailGym);
        EditText heightET = view.findViewById(R.id.etHeightGym);
        EditText weigthET = view.findViewById(R.id.etWeigthGym);
        EditText passET = view.findViewById(R.id.passwordETGym);
        EditText genderET = view.findViewById(R.id.genderETGym);


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
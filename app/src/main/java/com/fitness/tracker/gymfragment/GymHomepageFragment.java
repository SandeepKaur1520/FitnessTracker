package com.fitness.tracker.gymfragment;


import java.lang.String;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitness.tracker.R;


public class GymHomepageFragment extends Fragment {
    TextView stepDisplay;
    String stepValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        stepValue = getArguments().getString("Steps");
        View view =inflater.inflate(R.layout.gym_homepage_fragment,container,false);
        if(stepValue == null){
            stepValue="0";
        }
        stepDisplay= view.findViewById(R.id.StepDisplay);
        stepDisplay.setText(stepValue);



        return view;
    }
}

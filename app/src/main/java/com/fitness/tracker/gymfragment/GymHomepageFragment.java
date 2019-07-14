package com.fitness.tracker.gymfragment;


import java.lang.String;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fitness.tracker.R;
import com.fitness.tracker.stepsChart;


public class GymHomepageFragment extends Fragment {
    TextView stepDisplay;
    String stepValue;
    int waterCounter=0;

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

        LinearLayout llSteps = view.findViewById(R.id.llSteps);
        llSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), stepsChart.class);
                intent.putExtra("stepValue",stepValue);
                startActivity(intent);
            }
        });

        final TextView waterDisplay = view.findViewById(R.id.waterDisplay);
        Button plusBtn =view.findViewById(R.id.waterPlus);
        Button minusBtn =view.findViewById(R.id.waterMinus);

        waterDisplay.setText(String.valueOf(waterCounter));
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterCounter =waterCounter+1;
                waterDisplay.setText(String.valueOf(waterCounter));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waterCounter==0){
                    Toast.makeText(getActivity(),"You can't reduce Glass more",Toast.LENGTH_LONG).show();
                }else {
                    waterCounter = waterCounter - 1;
                    waterDisplay.setText(String.valueOf(waterCounter));
                }
            }
        });
        return view;
    }
}

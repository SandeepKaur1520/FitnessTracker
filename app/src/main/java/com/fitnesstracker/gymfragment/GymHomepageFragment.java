package com.fitnesstracker.gymfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstracker.R;
import com.fitnesstracker.stepsChart;


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
                int x =Integer.parseInt(stepValue);
                intent.putExtra("stepValue",x);
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

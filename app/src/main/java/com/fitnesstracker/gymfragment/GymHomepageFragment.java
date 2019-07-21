package com.fitnesstracker.gymfragment;


import android.content.Context;
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

import com.fitnesstracker.Calories;
import com.fitnesstracker.Daily_Goals;
import com.fitnesstracker.R;
import com.fitnesstracker.database.DatabaseHelper;
import com.fitnesstracker.stepsChart;


public class GymHomepageFragment extends Fragment {
    TextView stepDisplay,TvcalAchi;
    String stepValue,calValue,email,stepAchieve,waterAcheive,calAcheive,sleepAcheive;
    int waterCounter=0;
    Context context;
    DatabaseHelper db ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        db = new DatabaseHelper(context);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        stepValue = getArguments().getString("Steps");
        //We need to handle the calories calculation sysytem;

        email=getArguments().getString("email");
//        updateGoals();
        View view =inflater.inflate(R.layout.gym_homepage_fragment,container,false);
        if(stepValue == null){
            stepValue="0";
        }
        stepDisplay= view.findViewById(R.id.StepDisplay);
        stepDisplay.setText(stepValue);
        calValue="0";
        TextView sleepAch =view.findViewById(R.id.sleepAchieve);
        TextView waterAch =view.findViewById(R.id.waterAchieve);
        TextView stepAch =view.findViewById(R.id.stepsAchieve);
        TextView calAch =view.findViewById(R.id.caloriesAchieve);
        TextView calDisplay = view.findViewById(R.id.caloriesDisplay);


        calValue = db.getCalories(email);
        if(calValue==null){
            calValue="0";
        }
        calDisplay.setText(calValue);
        String goalsList[] = db.getDailyGoals(email);
        if(goalsList[0]=="Nothing"){
            stepAch.setText("of 1000");
            calAch.setText("Kcal 1000");
            waterAch.setText("Glass of 20");
            sleepAch.setText("Hrs of 24");
        }else{
            stepAchieve=goalsList[0];
            calAcheive=goalsList[1];
            waterAcheive=goalsList[2];
            sleepAcheive=goalsList[3];
            stepAch.setText("of "+stepAchieve);
            calAch.setText("Kcal of "+calAcheive);
            waterAch.setText("Glass of "+waterAcheive);
            sleepAch.setText("Hrs of "+sleepAcheive);
        }

        LinearLayout llCalories = view.findViewById(R.id.llCalories);
        llCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calories.class);
                int x =Integer.parseInt(calValue);

                intent.putExtra("calValue",x);
                intent.putExtra("calAcheive",calAcheive);
                startActivity(intent);
            }
        });

        LinearLayout llDailyGoals = view.findViewById(R.id.llDailyGoals);
        llDailyGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Daily_Goals.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });


        LinearLayout llSteps = view.findViewById(R.id.llSteps);
        llSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), stepsChart.class);
                int x =Integer.parseInt(stepValue);

                intent.putExtra("stepValue",x);
                intent.putExtra("stepAcheive",stepAchieve);
                startActivity(intent);
            }
        });

        final TextView waterDisplay = view.findViewById(R.id.waterDisplay);
        Button plusBtn =view.findViewById(R.id.waterPlus);
        Button minusBtn =view.findViewById(R.id.waterMinus);
        String water = db.getWaterCompleted(email);
        waterDisplay.setText(water);
        if(water.isEmpty())
            waterCounter = Integer.parseInt(water);
        else
            waterCounter = 0;

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterCounter =waterCounter+1;
                waterDisplay.setText(String.valueOf(waterCounter));
                db.updateWaterCompleted(email,String.valueOf(waterCounter));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waterCounter==0){
                    Toast.makeText(getActivity(),"You can't reduce Glass more",Toast.LENGTH_LONG).show();
                    db.updateWaterCompleted(email,String.valueOf(waterCounter));

                }else {
                    waterCounter = waterCounter - 1;
                    waterDisplay.setText(String.valueOf(waterCounter));
                    db.updateWaterCompleted(email,String.valueOf(waterCounter));

                }
            }
        });
        return view;
    }
}

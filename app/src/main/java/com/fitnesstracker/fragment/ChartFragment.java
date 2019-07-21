package com.fitnesstracker.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


public class ChartFragment extends Fragment {
    LineChart chart;
    Context context;
    DatabaseHelper db;
    String email;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.chartfragment,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart = view.findViewById(R.id.LineChart);
        email = getArguments().getString("email");
        Cursor resultSet = db.getCyclePeriodLength(email);
        ArrayList<Entry> cycleLength = new ArrayList<>();
        ArrayList<Entry> periodLength = new ArrayList<>();
        if(resultSet.moveToFirst()){
            do {
                int SrNo = Integer.parseInt(resultSet.getString(0));
                int cycle = Integer.parseInt(resultSet.getString(1));
                int period = Integer.parseInt(resultSet.getString(2));

                cycleLength.add(new Entry(SrNo,cycle));
                periodLength.add(new Entry(SrNo,period));
            }while (resultSet.moveToNext());
        }


        LineDataSet periodDataSet = new LineDataSet(periodLength, "Period Length");
        LineDataSet cycleDataSet = new LineDataSet(cycleLength, "Cycle Length");
        periodDataSet.setColor(getResources().getColor(R.color.Pink));
        cycleDataSet.setColor(getResources().getColor(R.color.LightBlue));

        XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr"};

        ValueFormatter formatter = new ValueFormatter() {

            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        LineData data = new LineData();
        data.addDataSet(periodDataSet);
        data.addDataSet(cycleDataSet);

        chart.setData(data);
        chart.animateX(1000);

        chart.invalidate();




    }

    public interface  onFragmentListener
    {
        void onFragment(Uri uri);
    }
}

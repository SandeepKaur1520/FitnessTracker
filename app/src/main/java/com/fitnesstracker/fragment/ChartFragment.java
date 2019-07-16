package com.fitnesstracker.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstracker.R;
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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.chartfragment,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart = view.findViewById(R.id.LineChart);;

        ArrayList<Entry> cycleLength = new ArrayList<>();
        cycleLength.add(new Entry(0, 30));
        cycleLength.add(new Entry(30,28));
        cycleLength.add(new Entry(60, 34));
        cycleLength.add(new Entry(90, 32));
        cycleLength.add(new Entry(120, 33));
        cycleLength.add(new Entry(150, 29));

        ArrayList<Entry> periodLength = new ArrayList<>();
        periodLength.add(new Entry(0, 4));
        periodLength.add(new Entry(30,5));
        periodLength.add(new Entry(60, 3));
        periodLength.add(new Entry(90, 4));
        periodLength.add(new Entry(120, 5));
        periodLength.add(new Entry(150, 3));


        LineDataSet periodDataSet = new LineDataSet(periodLength, "Period Length");
        LineDataSet cycleDataSet = new LineDataSet(cycleLength, "Cycle Length");
        periodDataSet.setColor(getResources().getColor(R.color.LightBlue));
        cycleDataSet.setColor(getResources().getColor(R.color.Pink));
       /* dataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
*/
        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Customizing x axis value
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

        // Setting Data
        LineData data = new LineData();
        data.addDataSet(periodDataSet);
        data.addDataSet(cycleDataSet);

        chart.setData(data);
        chart.animateX(1000);

        //refresh
        chart.invalidate();




    }

    public interface  onFragmentListener
    {
        void onFragment(Uri uri);
    }
}

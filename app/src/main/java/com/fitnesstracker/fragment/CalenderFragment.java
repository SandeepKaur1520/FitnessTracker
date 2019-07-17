package com.fitnesstracker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstracker.R;




public class CalenderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.calenderfragment,container,false);

        return view;
    }
    /*int currentMonth = YearMonth.now()
    int firstMonth = currentMonth.minusMonths(10)
    val lastMonth = currentMonth.plusMonths(10)
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
            calendarView.scrollToMonth(currentMonth)
*/


}

    /*val currentMonth = YearMonth.now()
    val firstMonth = currentMonth.minusMonths(10)
    val lastMonth = currentMonth.plusMonths(10)
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
            calendarView.scrollToMonth(currentMonth)
*/



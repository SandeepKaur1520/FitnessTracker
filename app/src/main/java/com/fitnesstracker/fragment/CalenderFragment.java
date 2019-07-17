package com.fitnesstracker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.fitnesstracker.R;
import com.fitnesstracker.period.PeriodDaysManager;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class CalenderFragment extends Fragment {

    private static Set<LocalDate> periodDays = new HashSet<>();
    private static Set<LocalDate> fertileDays = new HashSet<>();
    private static Set<LocalDate> ovulationDays = new HashSet<>();
    private PeriodDaysManager manager;
    CustomCalendarView calendarView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.calenderfragment,container,false);
        calendarView = view.findViewById(R.id.calendar_view);
        manager = new PeriodDaysManager(getContext());
        periodDays = manager.getHistoricPeriodDays();
        fertileDays = manager.getHistoricFertileDays();
        ovulationDays = manager.getHistoricOvulationDays();

        return view;

    }

    private void refreshCalendar(View view) {

        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setShowOverflowDate(true);
        calendarView.setDecorators(Collections.<DayDecorator>singletonList(new SampleDayDecorator()));
        calendarView.refreshCalendar(currentCalendar);
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == GATHER_NEW_PREFERENCES) {
//            periodDays = manager.getHistoricPeriodDays();
//            fertileDays = manager.getHistoricFertileDays();
//            ovulationDays = manager.getHistoricOvulationDays();
//            refreshCalendar();
//        }
//    }
    private class SampleDayDecorator implements DayDecorator {

        @Override
        public void decorate(DayView dayView) {
            LocalDate actualDate = new LocalDate(dayView.getDate());

            dayView.setBackgroundColor(getResources().getColor(R.color.neutralBackground));

            if (periodDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.periodDayBackground));
            }

            if (fertileDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.fertileDayBackground));
            }

            if (ovulationDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.ovulationDayBackground));
            }
        }
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



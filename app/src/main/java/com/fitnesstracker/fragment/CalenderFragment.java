package com.fitnesstracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DatabaseHelper;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import org.joda.time.LocalDate;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class CalenderFragment extends Fragment {

    private static Set<LocalDate> periodDays = new HashSet<>();
    private static Set<LocalDate> predictedPeriodDays = new HashSet<>();
    private static Set<LocalDate> fertileDays = new HashSet<>();
    private static Set<LocalDate> ovulationDays = new HashSet<>();

    ToggleButton toggleStart;
    ToggleButton toggleEnd;
    CustomCalendarView calendarView;
    String email, intialPeriodInfo[], periodHistory[];
    Context context;
    DatabaseHelper db;
    static Boolean isStartSelected = false;
    static Date bufferPressedDate;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        db = new DatabaseHelper(context);
    }

    int Periodlength, cyclelength;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calenderfragment, container, false);
        calendarView = view.findViewById(R.id.calendar_view);

        intialPeriodInfo = db.getPeriodInfo(email);//{periodLength,cycleLength,lastStartdate}

        email = getArguments().getString("email");
        updateLocalDateSetValue(email);


        Periodlength = db.getAvgPeriodLength(email);
        cyclelength = db.getAvgCycleLength(email);
        refreshCalendar(view);


        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(final Date date) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.dialog_periodcalenderpopup);
                dialog.setCancelable(true);
                RelativeLayout RLMood = dialog.findViewById(R.id.RLMoodpopup);
                final RelativeLayout RLFlowPopup = dialog.findViewById(R.id.RLPopupFlow);
                ToggleButton flowBtn = dialog.findViewById(R.id.toggle_flow);
                toggleStart = dialog.findViewById(R.id.toggle_periodStart);
                toggleEnd = dialog.findViewById(R.id.toggle_periodEnd);

                if (date.equals(bufferPressedDate) && isStartSelected) {
                    toggleEnd.setEnabled(false);
                    toggleEnd.setFocusable(false);
                } else {
                    toggleEnd.setEnabled(true);
                }

                toggleStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Date selectedDate = date;
                        if (isChecked) {
                            String SelectedDate, PredictedEndDate;

                            Date predictedEndDate = new Date();
                            int cycleLength = 0;
                            isStartSelected = isChecked;
                            bufferPressedDate = selectedDate;
                            int periodLength = db.getAvgPeriodLength(email);
                            try{
                                Date previousStartDate = db.getLatestStartPeriodDate(email);
                                cycleLength = getNumberOfDaysBetweenTwoDates(previousStartDate,selectedDate);

                            }catch (Exception exp){
                                Log.e("Expection startButton","Line 126 to 127");
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            /**predicting the period end date*/
                            predictedEndDate = calPeriodEndDate(selectedDate,email);
                            PredictedEndDate = sdf.format(predictedEndDate);
                            SelectedDate = sdf.format(selectedDate);
                            Boolean status = db.insertPeriodInfo(email, String.valueOf(periodLength), String.valueOf(cycleLength), SelectedDate, PredictedEndDate, "start");
                            Log.e("DAta in Database","Status = "+status);
                            updateLocalDateSetValue(email);
                            calendarView.setDecorators(Collections.<DayDecorator>singletonList(new SampleDayDecorator()));

                        } else {
                            Date nearestDate = getNearestStartDate(selectedDate,email);
                            Log.e("Nearest Date",""+nearestDate);
//                            Boolean status = db.removePeriodInfo(email, "start");
                            updateLocalDateSetValue(email);
                        }
                        dialog.dismiss();

                    }
                });

                toggleEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Date nearestDate = getNearestStartDate(date,email);
                        Log.e("Nearest Date",""+nearestDate);
                        dialog.dismiss();

                    }
                });
                flowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            RLFlowPopup.setVisibility(View.VISIBLE);
                        } else {
                            RLFlowPopup.setVisibility(View.GONE);
                        }
                    }
                });

                RLMood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialogMood = new Dialog(getContext());
                        dialogMood.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogMood.setContentView(R.layout.dialog_mood);
                        dialogMood.setCancelable(true);
                        dialog.dismiss();
                        dialogMood.show();
                    }
                });

                dialog.show();

            }

            @Override
            public void onMonthChanged(Date date) {

            }
        });
        return view;

    }

    private Date getNearestStartDate(Date selectedDate, String email) {

        Date nearestDate = new Date();
        List<Date> dates = new ArrayList<Date>();
        Cursor resultSet = db.getPeriodStartDates(email);
        if(resultSet.moveToFirst()){
            do{
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if(resultSet.moveToFirst()){
                    try {
                        date = sdf.parse(resultSet.getString(0));
                    } catch (Exception exp) {
                        Log.e("Expection ","in getIntialPeriodDays method while praseing dates");
                    }
                }
                Calendar sDate = Calendar.getInstance();
                Calendar eDate = Calendar.getInstance();
                sDate.setTime(date);
                eDate.setTime(selectedDate);
                if(sDate.before(eDate)){
                    dates.add(date);
                }
//                if(date.compareTo(selectedDate)==-1){
//                    dates.add(date);
//                }
            }while(resultSet.moveToNext());

        }
//        nearestDate = Collections.min(dates, new Comparator<Date>() {
//            @Override
//            public int compare(Date d1, Date d2) {
//                long diff1 = Math.abs(d1.getTime() - now);
//                long diff2 = Math.abs(d2.getTime() - now);
//                return Long.compare(diff1,diff2);
//            }
//        });
        int size = dates.size();
        nearestDate = dates.get(size);

        return nearestDate;
    }

    private int getNumberOfDaysBetweenTwoDates(Date previousStartDate, Date selectedDate) {
        int numberOfDays=0;
        Calendar sDate = Calendar.getInstance();
        Calendar eDate = Calendar.getInstance();
        sDate.setTime(previousStartDate);
        eDate.setTime(selectedDate);
        while (eDate.before(sDate)) {
            numberOfDays++;
        }
        return numberOfDays;
    }

    public Date calPeriodEndDate(Date startDate,String email){
        Date peroidEndDate;
        String PeroidEndDate;
        int periodLength = db.getAvgPeriodLength(email);
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DAY_OF_MONTH,periodLength);
        peroidEndDate = c.getTime();
        return peroidEndDate;
    }

    public Date calCycleStartDate(Date oldStartDate,String email){
        Date cycleStartDate;
        String CycleStartDate;
        int cycleLength = db.getAvgCycleLength(email);
        Calendar c = Calendar.getInstance();
        c.setTime(oldStartDate);
        c.add(Calendar.DAY_OF_MONTH,cycleLength);
        cycleStartDate = c.getTime();
        return cycleStartDate;
    }

    public Set<LocalDate>getDaysBetweenDates(Date startdate,Date enddate){
        Set<LocalDate> days = new HashSet<>();
        Calendar sDate = Calendar.getInstance();
        Calendar eDate = Calendar.getInstance();
        sDate.setTime(startdate);
        eDate.setTime(enddate);
        while (sDate.before(eDate)) {
            LocalDate localDate = new LocalDate(sDate.getTime());
            days.add(localDate);
            sDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    private Set<LocalDate> getPredictedDays(String email) {
        Set<LocalDate> days = new HashSet<>();
        Cursor resultSet = db.getLatestCompletedPeriods(email);
        if (resultSet.moveToFirst()) {
            do {
                int cycleLength = Integer.parseInt(resultSet.getString(3));
                int periodLength = Integer.parseInt(resultSet.getString(2));
                String OldStartDate = resultSet.getString(4);
                String OldEndDate = resultSet.getString(5);
                Date newStartDate, newEndDate,oldStartDate=new Date() ;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    oldStartDate = sdf.parse(OldStartDate);
                } catch (Exception e) {
                  Log.e("Expection","in Predicted Days Method"+e.toString());
                }
                Calendar c = Calendar.getInstance();
                c.setTime(oldStartDate);
                c.add(Calendar.DAY_OF_MONTH,cycleLength);
                newStartDate = c.getTime();

                String PeroidEndDate;
                c.setTime(newStartDate);
                c.add(Calendar.DAY_OF_MONTH,periodLength);
                newEndDate =  c.getTime();
                Calendar sDate = Calendar.getInstance();
                Calendar eDate = Calendar.getInstance();

                sDate.setTime(newStartDate);
                eDate.setTime(newEndDate);
                while (sDate.before(eDate)) {
                    LocalDate localDate = new LocalDate(sDate.getTime());
                    days.add(localDate);
                    sDate.add(Calendar.DAY_OF_MONTH, 1);
                }

            } while (resultSet.moveToNext());
        }
        return days;
    }

    private void refreshCalendar(View view) {

        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setShowOverflowDate(true);
        calendarView.setDecorators(Collections.<DayDecorator>singletonList(new SampleDayDecorator()));
        calendarView.refreshCalendar(currentCalendar);
    }

    private class SampleDayDecorator implements DayDecorator {

        @Override
        public void decorate(DayView dayView) {
            LocalDate actualDate = new LocalDate(dayView.getDate());

            dayView.setBackgroundColor(getResources().getColor(R.color.neutralBackground));

            if (periodDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.Pink));
            }
            if (predictedPeriodDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.lightPink));
            }

            if (fertileDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.fertileDayBackground));
            }

            if (ovulationDays.contains(actualDate)) {
                dayView.setBackgroundColor(getResources().getColor(R.color.LightBlue));
            }
        }
    }

    public Set<LocalDate>getPeriodDays(String email){
        Set<LocalDate> days = new HashSet<>();
        Cursor resultSet = db.getPeriodDates(email);
        if(resultSet.moveToFirst()){
            do{
                String StartDate=resultSet.getString(0);
                String EndDate=resultSet.getString(1);
                Date startDate=new Date(),endDate=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = sdf.parse(StartDate);
                    endDate = sdf.parse(EndDate);
                } catch (Exception exp) {
                    Log.e("Expection ","in getIntialPeriodDays method while praseing dates");
                }
                Calendar sDate = Calendar.getInstance();
                Calendar eDate = Calendar.getInstance();
                sDate.setTime(startDate);
                eDate.setTime(endDate);
                while (sDate.before(eDate)) {
                    LocalDate localDate = new LocalDate(sDate.getTime());
                    days.add(localDate);
                    sDate.add(Calendar.DAY_OF_MONTH, 1);
                }

            }while (resultSet.moveToNext());
        }
        return days;
    }

    public Set<LocalDate>getOvulationDays(String email){
        Set<LocalDate> days = new HashSet<>();
        Cursor resultSet = db.getOvulDates(email);
        if(resultSet.moveToFirst()){
            do{
                String StartDate=resultSet.getString(0);
                String EndDate=resultSet.getString(1);
                String NewStartDate=resultSet.getString(2);
                Date startDate=new Date(),endDate=new Date(),newStartDate =new Date(),lastfourteendays = new Date(),firstSevenDays =new  Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = sdf.parse(StartDate);
                    endDate = sdf.parse(EndDate);
                    newStartDate =sdf.parse(NewStartDate);
                } catch (Exception exp) {
                    Log.e("Expection ","in getIntialPeriodDays method while praseing dates");
                }
                Calendar c = Calendar.getInstance();
                c.setTime(endDate);
                c.add(Calendar.DAY_OF_MONTH, 6);
                firstSevenDays = c.getTime();

                c.setTime(newStartDate);
                c.add(Calendar.DAY_OF_MONTH, -13);
                lastfourteendays = c.getTime();
                Calendar sDate = Calendar.getInstance();
                Calendar eDate = Calendar.getInstance();
                sDate.setTime(firstSevenDays);
                eDate.setTime(lastfourteendays);
                while (sDate.before(eDate)) {
                    LocalDate localDate = new LocalDate(sDate.getTime());
                    days.add(localDate);
                    sDate.add(Calendar.DAY_OF_MONTH, 1);
                }

            }while (resultSet.moveToNext());
        }
        return days;

    }

    private Set<LocalDate> getIntialOvulationDays(String email) {
        Set<LocalDate> days = new HashSet<>();
        try {
            Cursor resultSet = db.getLatestCompletedPeriods(email);
            int cyclelength = db.getAvgCycleLength(email);
            if (resultSet.moveToFirst()) {
                do {
                    String OldStartDate = resultSet.getString(4);
                    String OldEndDate = resultSet.getString(5);
                    Date oldStartDate, oldEndDate, newStartDate, firstSevenDays, lastfourteendays;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    oldStartDate = sdf.parse(OldStartDate);
                    oldEndDate = sdf.parse(OldEndDate);

                    Calendar sDate = Calendar.getInstance();
                    Calendar eDate = Calendar.getInstance();
                    Calendar c = Calendar.getInstance();
                    c.setTime(oldStartDate);
                    c.add(Calendar.DAY_OF_MONTH, cyclelength);
                    newStartDate = c.getTime();

                    c.setTime(oldEndDate);
                    c.add(Calendar.DAY_OF_MONTH, 6);
                    firstSevenDays = c.getTime();

                    c.setTime(newStartDate);
                    c.add(Calendar.DAY_OF_MONTH, -13);
                    lastfourteendays = c.getTime();

                    sDate.setTime(firstSevenDays);
                    eDate.setTime(lastfourteendays);
                    while (sDate.before(eDate)) {
                        LocalDate localDate = new LocalDate(sDate.getTime());
                        days.add(localDate);
                        sDate.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } while (resultSet.moveToNext());
            }

        } catch (Exception e) {
            return null;
            //            Log.e("Expection in Perdiodays",e.toString());
        }
        return days;
    }


    private void updateLocalDateSetValue(String email) {
        periodDays.clear();
        ovulationDays.clear();
        periodDays = getPeriodDays(email);
        ovulationDays = getOvulationDays(email);
        predictedPeriodDays = getPredictedDays(email);

    }


}

/**private Set<LocalDate> getIntialOvulationDays(String email) {
 Set<LocalDate> days = new HashSet<>();
 try {
 Cursor resultSet = db.getLatestCompletedPeriods(email);
 int cyclelength = db.getAvgCycleLength(email);
 if (resultSet.moveToFirst()) {
 do {
 String OldStartDate = resultSet.getString(4);
 String OldEndDate = resultSet.getString(5);
 Date oldStartDate, oldEndDate, newStartDate, firstSevenDays, lastfourteendays;
 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 oldStartDate = sdf.parse(OldStartDate);
 oldEndDate = sdf.parse(OldEndDate);

 Calendar sDate = Calendar.getInstance();
 Calendar eDate = Calendar.getInstance();
 Calendar c = Calendar.getInstance();
 c.setTime(oldStartDate);
 c.add(Calendar.DAY_OF_MONTH, cyclelength);
 newStartDate = c.getTime();

 c.setTime(oldEndDate);
 c.add(Calendar.DAY_OF_MONTH, 6);
 firstSevenDays = c.getTime();

 c.setTime(newStartDate);
 c.add(Calendar.DAY_OF_MONTH, -13);
 lastfourteendays = c.getTime();

 sDate.setTime(firstSevenDays);
 eDate.setTime(lastfourteendays);
 while (sDate.before(eDate)) {
 LocalDate localDate = new LocalDate(sDate.getTime());
 days.add(localDate);
 sDate.add(Calendar.DAY_OF_MONTH, 1);
 }
 } while (resultSet.moveToNext());
 }

 } catch (Exception e) {
 return null;
 //            Log.e("Expection in Perdiodays",e.toString());
 }
 return days;
 }*/

/** private Set<LocalDate> getIntialPeriodDays(String email) {
 Set<LocalDate> days = new HashSet<>();
 Cursor resultSet = db.getInCompletedPeriodsHistory(email);
 if (resultSet.moveToFirst()) {
 do {
 String StartDate = resultSet.getString(4);
 String EndDate = resultSet.getString(5);
 Date startDate =new Date();
 Date endDate=new Date();
 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 try {
 startDate = sdf.parse(StartDate);
 endDate = sdf.parse(EndDate);
 } catch (Exception exp) {
 Log.e("Expection ","in getIntialPeriodDays method while praseing dates");
 }
 days.addAll(getDaysBetweenDates(startDate,endDate));//
 } while (resultSet.moveToNext());
 }
 return days;
 }*/

/**Start button
 try {
 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
 Set<LocalDate> newperiodpredicted = new HashSet<>();
 Set<LocalDate> oldperiodpredicted = new HashSet<>();
 Set<LocalDate> periodDaystemp = new HashSet<>();
 Calendar c = Calendar.getInstance();
 Date  newEndDate,newStartDate=date;
 String NewStartDate, NewEndDate;
 String StartDate,EndDate;
 Date startDate,endDate;

 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 NewStartDate = dateFormat.format(date);
 Log.e("Date = ", NewStartDate);
 if(isChecked) {
 bufferPressedDate=date;
 isStartSelected=true;
 c.setTime(newStartDate);
 c.add(Calendar.DATE, Periodlength);
 NewEndDate = sdf.format(c.getTime());
 newEndDate = sdf.parse(NewEndDate);
 Log.e("Last End Date", newEndDate.toString());
 Log.e("Last End Date String", NewEndDate.toString());
 Calendar sDate = Calendar.getInstance();
 Calendar eDate = Calendar.getInstance();
 sDate.setTime(newStartDate);
 eDate.setTime(newEndDate);
 while (sDate.before(eDate)) {
 LocalDate localDate = new LocalDate(sDate.getTime());
 periodDaystemp.add(localDate);
 sDate.add(Calendar.DAY_OF_MONTH, 1);
 }
 c.setTime(newStartDate);
 c.add(Calendar.DATE, cyclelength);
 StartDate = sdf.format(c.getTime());
 startDate = sdf.parse(StartDate);

 c.setTime(startDate);
 c.add(Calendar.DATE, Periodlength);
 EndDate = sdf.format(c.getTime());
 endDate = sdf.parse(EndDate);

 sDate.setTime(startDate);
 eDate.setTime(endDate);
 while (sDate.before(eDate)) {
 LocalDate localDate = new LocalDate(sDate.getTime());
 newperiodpredicted.add(localDate);
 sDate.add(Calendar.DAY_OF_MONTH, 1);

 }
 refreshCalendar(view);

 periodDays.addAll(periodDaystemp);
 oldperiodpredicted.addAll(predictedPeriodDays);
 predictedPeriodDays.clear();
 predictedPeriodDays.addAll(newperiodpredicted);
 toggleStart.setChecked(true);
 }else{

 periodDays.removeAll(periodDaystemp);
 predictedPeriodDays.clear();
 predictedPeriodDays.addAll(oldperiodpredicted);


 refreshCalendar(view);
 toggleStart.setChecked(false);
 }

 }catch (Exception e){

 }
 isStartSelected=isChecked;
 */

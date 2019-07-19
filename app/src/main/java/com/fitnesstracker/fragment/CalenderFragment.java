package com.fitnesstracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fitnesstracker.R;
import com.fitnesstracker.database.DatabaseHelper;
import com.fitnesstracker.period.PeriodDaysManager;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class CalenderFragment extends Fragment {

    private static Set<LocalDate> periodDays = new HashSet<>();
    private static Set<LocalDate> predictedPeriodDays = new HashSet<>();
    private static Set<LocalDate> fertileDays = new HashSet<>();
    private static Set<LocalDate> ovulationDays = new HashSet<>();
    private PeriodDaysManager manager;
    CustomCalendarView calendarView;
    String email,intialPeriodInfo[],periodHistory[];
    Context context;
    DatabaseHelper db ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
        db = new DatabaseHelper(context);
    }
    int Periodlength , cyclelength;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.calenderfragment,container,false);
        calendarView = view.findViewById(R.id.calendar_view);
        manager = new PeriodDaysManager(getContext());
        //periodDays = manager.getHistoricPeriodDays();
//        fertileDays = manager.getHistoricFertileDays();
//        ovulationDays = manager.getHistoricOvulationDays();

        email = getArguments().getString("email");
        intialPeriodInfo = db.getPeriodInfo(email);//{periodLength,cycleLength,lastStartdate}

        periodDays = getIntialPeriodDays(email);
        ovulationDays = getIntialOvulationDays(email);
        predictedPeriodDays =getPredictedDays(email);

//        addPeriodIntial(email,intialPeriodInfo);
        Periodlength = Integer.parseInt(intialPeriodInfo[0]);
        cyclelength = Integer.parseInt(intialPeriodInfo[1]);


        refreshCalendar(view);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(final Date date) {
                Toast.makeText(getContext(),""+date,Toast.LENGTH_LONG).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_periodcalenderpopup);
                dialog.setCancelable(true);
                RelativeLayout RLMood = dialog.findViewById(R.id.RLMoodpopup);
                final RelativeLayout RLFlowPopup = dialog.findViewById(R.id.RLPopupFlow);
                ToggleButton flowBtn = dialog.findViewById(R.id.toggle_flow);
                final ToggleButton toggleStart = dialog.findViewById(R.id.toggle_periodStart);
               toggleStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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


                   }
               });


                flowBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            RLFlowPopup.setVisibility(View.VISIBLE);
                        }else {
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

    private Set<LocalDate> getPredictedDays(String email) {
        Set<LocalDate> days = new HashSet<>();
        try {
            Cursor resultSet = db.getLatestCompletedPeriods(email);
            int cyclelength =db.getAvgCycleLength(email);
            int peroidlength =db.getAvgPeriodLength(email);

            if (resultSet.moveToFirst()) {
                do {

                    String OldStartDate = resultSet.getString(4);
                    String OldEndDate = resultSet.getString(5);
                    Date oldStartDate, oldEndDate,newStartDate,newEndDate;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    oldStartDate = sdf.parse(OldEndDate);
                    oldEndDate = sdf.parse(OldEndDate);
                    Calendar sDate = Calendar.getInstance();
                    Calendar eDate = Calendar.getInstance();
                    Calendar c =Calendar.getInstance();
                    c.setTime(oldStartDate);
                    c.add(Calendar.DAY_OF_MONTH,cyclelength);
                    newStartDate = c.getTime();

                    c.setTime(newStartDate);
                    c.add(Calendar.DAY_OF_MONTH,peroidlength);
                    newEndDate = c.getTime();


                    sDate.setTime(newStartDate);
                    eDate.setTime(newEndDate);
                    while (sDate.before(eDate)) {
                        LocalDate localDate = new LocalDate(sDate.getTime());
                        days.add(localDate);
                        sDate.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } while (resultSet.moveToNext());
            }

        }catch (Exception e){
            return null;
//            Log.e("Expection in Perdiodays",e.toString());
        }
        return days;
    }

    private Set<LocalDate> getIntialOvulationDays(String email) {
        Set<LocalDate> days = new HashSet<>();
        try {
            Cursor resultSet = db.getLatestCompletedPeriods(email);
            int cyclelength =db.getAvgCycleLength(email);
            if (resultSet.moveToFirst()) {
                do {

                    String OldStartDate = resultSet.getString(4);
                    String OldEndDate = resultSet.getString(5);
                    Date oldStartDate, oldEndDate,newStartDate,firstSevenDays,lastfourteendays;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    oldStartDate = sdf.parse(OldEndDate);
                    oldEndDate = sdf.parse(OldEndDate);
                    Calendar sDate = Calendar.getInstance();
                    Calendar eDate = Calendar.getInstance();
                    Calendar c =Calendar.getInstance();
                    c.setTime(oldStartDate);
                    c.add(Calendar.DAY_OF_MONTH,cyclelength);
                    newStartDate = c.getTime();

                    c.setTime(oldEndDate);
                    c.add(Calendar.DAY_OF_MONTH,6);
                    firstSevenDays = c.getTime();

                    c.setTime(newStartDate);
                    c.add(Calendar.DAY_OF_MONTH,-13);
                    lastfourteendays=c.getTime();

                    sDate.setTime(firstSevenDays);
                    eDate.setTime(lastfourteendays);
                    while (sDate.before(eDate)) {
                        LocalDate localDate = new LocalDate(sDate.getTime());
                        days.add(localDate);
                        sDate.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } while (resultSet.moveToNext());
            }

        }catch (Exception e){
            return null;
//            Log.e("Expection in Perdiodays",e.toString());
        }
        return days;
    }

    //return the set of completed period days
    private Set<LocalDate> getIntialPeriodDays(String email) {
        Set<LocalDate> days = new HashSet<>();
        try {
            Cursor resultSet = db.getCompletedPeriodsHistory(email);

            if (resultSet.moveToFirst()) {
                do {

                    String StartDate = resultSet.getString(4);
                    String EndDate = resultSet.getString(5);
                    Date startDate, endDate;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    startDate = sdf.parse(StartDate);
                    endDate = sdf.parse(EndDate);
                    Calendar sDate = Calendar.getInstance();
                    Calendar eDate = Calendar.getInstance();
                    sDate.setTime(startDate);
                    eDate.setTime(endDate);
                    while (sDate.before(eDate)) {
                        LocalDate localDate = new LocalDate(sDate.getTime());
                        days.add(localDate);
                        sDate.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } while (resultSet.moveToNext());
            }

        }catch (Exception e){
            return null;
//            Log.e("Expection in Perdiodays",e.toString());
        }
        return days;

    }

//    private void addPeriodIntial(String email, String[] intialPeriodInfo) {
//        try {
//        Periodlength = Integer.parseInt(intialPeriodInfo[0]);
//        cyclelength = Integer.parseInt(intialPeriodInfo[1]);
//        String StartDate,EndDate,LastEndDate;
//        Date lastStartDate,lastEndDate,startDate,endDate;
//
//        Calendar c = Calendar.getInstance();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        lastStartDate = sdf.parse(intialPeriodInfo[2]);
//
//       /**Calculating lastend Date*/
//        c.setTime(lastStartDate);
//        Log.e("Last Start Date",lastStartDate.toString());
//        c.add(Calendar.DATE,Periodlength);
//        LastEndDate=sdf.format(c.getTime());
//        lastEndDate = sdf.parse(LastEndDate);
//        Log.e("Last End Date",lastEndDate.toString());
//        Log.e("Last End Date String",LastEndDate.toString());
//
//
//        Calendar sDate = Calendar.getInstance();
//        Calendar eDate = Calendar.getInstance();
//        sDate.setTime(lastStartDate);
//        eDate.setTime(lastEndDate);
//        while (sDate.before(eDate)){
//            LocalDate localDate = new LocalDate(sDate.getTime());
//            periodDays.add(localDate);
//            sDate.add(Calendar.DAY_OF_MONTH,1);
//
//        }
//
//        /**Calculating perdicted period Start and end Date*/
//
//        c.setTime(lastStartDate);
//        c.add(Calendar.DATE,cyclelength);
//        StartDate = sdf.format(c.getTime());
//        startDate = sdf.parse(StartDate);
//
//        c.setTime(startDate);
//        c.add(Calendar.DATE,Periodlength);
//        EndDate=sdf.format(c.getTime());
//        endDate = sdf.parse(EndDate);
//
//        sDate.setTime(startDate);
//        eDate.setTime(endDate);
//
//        while (sDate.before(eDate)){
//            LocalDate localDate = new LocalDate(sDate.getTime());
//            predictedPeriodDays.add(localDate);
//            sDate.add(Calendar.DAY_OF_MONTH,1);
//
//        }
//
//
//        }catch (Exception es){
//            Log.e("ExceptionSDF","");
//            es.printStackTrace();
//        }
//
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private java.time.LocalDate convertToLocalDate(Date time) {
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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



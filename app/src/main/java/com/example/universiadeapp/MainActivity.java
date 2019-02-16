package com.example.universiadeapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.universiadeapp.Models.Schedule;
import com.example.universiadeapp.adapters.ScheduleAdapter;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    List<Schedule> schedules = new ArrayList<>();
    List<Schedule> dayFirst = new ArrayList<>();
    List<Schedule> daySecond = new ArrayList<>();
    ScheduleAdapter adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitialDayFirst();
        setInitialDaySecond();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        adapter = new ScheduleAdapter(this, schedules);

        recyclerView.setAdapter(adapter);



        final Map<Integer, List<Schedule>> dateSchedule = new HashMap<>();
        dateSchedule.put( 2, dayFirst);
        dateSchedule.put( 3, daySecond);





        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019,Calendar.MARCH,2);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019,Calendar.MARCH,12);


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                schedules.clear();
                schedules.addAll(dateSchedule.get(date.get(Calendar.DAY_OF_MONTH)));
                adapter.notifyDataSetChanged();
            }
        });

        horizontalCalendar.selectDate(startDate, true);



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setInitialDayFirst(){

        dayFirst.add(new Schedule("Горные лыжи", "Бобровый лог", "14:00"));
        dayFirst.add(new Schedule("Биатлон", "Стадион Ветлужанка", "15:00"));
        dayFirst.add(new Schedule("Конькобежный спорт", "Ледовая Арена", "16:00"));
        dayFirst.add(new Schedule("Фигурное катание", "Ледовый дворец", "17:00"));



    }

    private void setInitialDaySecond(){

        daySecond.add(new Schedule("Сноубординг", "Бобровый лог", "14:00"));
        daySecond.add(new Schedule("Хоккей", "Стадион Ветлужанка", "15:00"));
        daySecond.add(new Schedule("Фристайл", "Ледовая Арена", "16:00"));
        daySecond.add(new Schedule("Лыжные гонки", "Ледовый дворец", "17:00"));

    }


}

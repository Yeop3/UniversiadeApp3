package com.example.universiadeapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.ScheduleAdapter;
import com.example.universiadeapp.models.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment {
    List<Schedule> schedules = new ArrayList<>();
    List<Schedule> dayFirst = new ArrayList<>();
    List<Schedule> daySecond = new ArrayList<>();

    ScheduleAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setInitialDayFirst();
//        setInitialDaySecond();
        setInitialSchedules();

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        recyclerView = rootView.findViewById(R.id.list);

        adapter = new ScheduleAdapter(getActivity(), schedules);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final Map<Integer, List<Schedule>> dateSchedule = new HashMap<>();
        dateSchedule.put(2, dayFirst);
        dateSchedule.put(3, daySecond);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, Calendar.MARCH, 2);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, Calendar.MARCH, 3);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(startDate)
                .configure().showBottomText(false)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                schedules.clear();
                schedules.addAll(dateSchedule.get(date.get(Calendar.DAY_OF_MONTH)));
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void setInitialDayFirst() {
        dayFirst.add(new Schedule("Горные лыжи", "Бобровый лог", "14:00"));
        dayFirst.add(new Schedule("Биатлон", "Стадион Ветлужанка", "15:00"));
        dayFirst.add(new Schedule("Конькобежный спорт", "Ледовая Арена", "16:00"));
        dayFirst.add(new Schedule("Фигурное катание", "Ледовый дворец", "17:00"));
    }

//    private void setInitialDaySecond() {
//        daySecond.add(new Schedule("Сноубординг", "Бобровый лог", "14:00"));
//        daySecond.add(new Schedule("Хоккей", "Стадион Ветлужанка", "15:00"));
//        daySecond.add(new Schedule("Фристайл", "Ледовая Арена", "16:00"));
//        daySecond.add(new Schedule("Лыжные гонки", "Ледовый дворец", "17:00"));
//    }

    private void setInitialSchedules() {
        schedules.addAll(dayFirst);
    }
}

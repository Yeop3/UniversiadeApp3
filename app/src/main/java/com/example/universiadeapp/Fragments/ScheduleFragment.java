package com.example.universiadeapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.Models.Schedule;
import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.ScheduleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<Schedule> schedules = new ArrayList<>();
    List<Schedule> dayFirst = new ArrayList<>();
    List<Schedule> daySecond = new ArrayList<>();
    ScheduleAdapter adapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setInitialDayFirst();
        setInitialDaySecond();
        setInitialSchedules();

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        adapter = new ScheduleAdapter(getContext(), schedules);

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


        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(startDate)
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

    private void setInitialSchedules(){

        schedules.add(new Schedule("Горные лыжи", "Бобровый лог", "14:00"));
        schedules.add(new Schedule("Биатлон", "Стадион Ветлужанка", "15:00"));
        schedules.add(new Schedule("Конькобежный спорт", "Ледовая Арена", "16:00"));
        schedules.add(new Schedule("Фигурное катание", "Ледовый дворец", "17:00"));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

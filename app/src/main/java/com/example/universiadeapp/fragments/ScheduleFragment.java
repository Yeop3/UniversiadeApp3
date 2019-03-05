package com.example.universiadeapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.universiadeapp.DetailActivity;
import com.example.universiadeapp.R;
import com.example.universiadeapp.ScheduleDetail;
import com.example.universiadeapp.adapters.ScheduleAdapter;
import com.example.universiadeapp.models.Schedule;
import com.example.universiadeapp.utils.OnClick;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment implements OnClick {
    List<Schedule> schedules = new ArrayList<>();
    List<Schedule> schedule = new ArrayList<>();
    List<Schedule> timeListSchedule = new ArrayList<>();
    HorizontalCalendar horizontalCalendar;
    ScheduleAdapter adapter;
    RecyclerView recyclerView;
    SharedPreferences sPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        sPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        timeListSchedule.clear();
        timeListSchedule.addAll(Arrays.asList(new Gson().fromJson(sPref.getString("schedule", "[]"), Schedule[].class)));

        recyclerView = rootView.findViewById(R.id.list);

        adapter = new ScheduleAdapter(getActivity(), schedule);
        recyclerView.setAdapter(adapter);

        adapter.mySetOnClickListener(this);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, Calendar.MARCH, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, Calendar.MARCH, 12);

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure().showBottomText(false)
                .end()
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                schedule.clear();
                for (int j = 0; j < timeListSchedule.size(); j++) {
                    if (timeListSchedule.get(j).getDateEvent() == date.get(Calendar.DAY_OF_MONTH))
                        schedule.add(timeListSchedule.get(j));
                }
                adapter.notifyDataSetChanged();
            }
        });

        new newsParse().execute();

        return rootView;
    }

    @Override
    public void setOnClickListener(int position) {
        Intent intent = new Intent(getContext(), ScheduleDetail.class);
        intent.putExtra("detail", schedule.get(position));
        startActivity(intent);
    }

    private class newsParse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            horizontalCalendar.goToday(true);
        }


        @Override
        protected Void doInBackground(Void... params) {
            Document mDocument;
            Elements mElements;
            int Date = 0;
            String Time = null;
            SimpleDateFormat FullDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            SimpleDateFormat partDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat dateFormat = new SimpleDateFormat("d");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            try {
                mDocument = Jsoup.connect("https://www.championat.com/other/_krasnoyarsk2019/tournament/535/calendar/").get();

                mElements = mDocument.select(".tournament-calendar__row");

                int mElementsSize = mElements.size();

                for (int i = 0; i < mElementsSize; i++) {

                    Elements dateParse = mDocument.select(".tournament-calendar__date").select("span").eq(i);
                    String FullDate = dateParse.text();
                    java.util.Date fullDate = null;
                    try {
                        fullDate = FullDateFormat.parse(FullDate);
                        fullDate = new Date(fullDate.getTime() + 14400000);
                    } catch (ParseException e) {
                        try {
                            fullDate = partDateFormat.parse(FullDate);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    if (fullDate != null) {
                        Date = Integer.valueOf(dateFormat.format(fullDate));
                        Time = timeFormat.format(fullDate);
                    } else {
                        Date = Integer.valueOf(dateFormat.format(fullDate));
                    }


                    Elements typeParse = mDocument.select(".tournament-calendar__group").select("span").eq(i);
                    String Type = typeParse.text();

                    Elements dataParse = mDocument.select(".tournament-calendar__name").select("span").eq(i);
                    String Data = dataParse.text();

                    Elements urlParse = mDocument.select(".tournament-calendar__name").select("a").eq(i);
                    String Url = urlParse.attr("href");

                    schedules.add(new Schedule(Data, Date, Time, Url, Type));
                }

                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("schedule", new GsonBuilder().create().toJson(schedules));
                editor.putBoolean("parse", true);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();
        }
    }

}

package com.example.universiadeapp.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.ScheduleAdapter;
import com.example.universiadeapp.models.News;
import com.example.universiadeapp.models.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ScheduleFragment extends Fragment {
    List<Schedule> schedules = new ArrayList<>();
    List<Schedule> schedule = new ArrayList<>();
    HorizontalCalendar horizontalCalendar;

    private static ProgressDialog mProgressDialog;
//    type
    String hokey_group_stage = "Хоккей: Групповой этап";
    String bandy_group_stage = "Хоккей с мячом: Групповой этап";
    String biathlon = "";
    String curling_group_stage = "Керлинг: Групповой этап";
    String cross_country_skiing_5 = "Лыжные гонки: Финал 5км";
    String snowboard_qualif = "Сноуборд-кросс: Квалификация";
    String ski_orienteering = "";
    String short_track = "";
    String freestyle_sking = "";
    String figure_skating = "";
    String alpin_skiing = "Горные лыжи: Финал";

//    place
    String hokey_men_place = "Ледовый дворец «Кристалл арена»";
    String hokey_women_place = "Крытый каток «Первомайский»";
    String bandy_place = "Стадион «Енисей»";
    String alpin_skiing_place = "Фанпарк «Бобровый лог»";
    String curling_place = "Дворец спорта им. И. Ярыгина";
    String freestyle_skiing_place = "Кластер «Сопка»";
    String figure_skating_place = "Платинум Арена";
    String short_track_place = "Комплекс «Арена. Север»";
    String ski_orienteering_place = "СТК «Академия зимних видов спорта»";



    ScheduleAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        new newsParse().execute();

        recyclerView = rootView.findViewById(R.id.list);

        adapter = new ScheduleAdapter(getActivity(), schedule);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, Calendar.MARCH, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, Calendar.MARCH, 12);

        horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .defaultSelectedDate(startDate)
                .configure().showBottomText(false)
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                schedule.clear();
                    for (int j = 0; j<schedules.size();j++) {
                        if (schedules.get(j).getDateEvent() == date.get(Calendar.DAY_OF_MONTH))
                            schedule.add(schedules.get(j));
                    }
                adapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }

    private class newsParse extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
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

           try{
                mDocument = Jsoup.connect("https://www.championat.com/other/_krasnoyarsk2019/tournament/535/calendar/").get();


                mElements = mDocument.select(".tournament-calendar__row");

                int mElementsSize = mElements.size();

                for (int i = 0; i < mElementsSize; i++) {

                    Elements dateParse = mDocument.select(".tournament-calendar__date").select("span").eq(i);
                    String FullDate = dateParse.text();
                    java.util.Date fullDate = null;
                    try {
                        fullDate = FullDateFormat.parse(FullDate);
                        fullDate = new Date(fullDate.getTime()+14400000);
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
                    }else {
                        Date = Integer.valueOf(dateFormat.format(fullDate));
                    }


                    Elements typeParse = mDocument.select(".tournament-calendar__group").select("span").eq(i);
                    String Type = typeParse.text();

                    Elements dataParse = mDocument.select(".tournament-calendar__name").select("span").eq(i);
                    String Data = dataParse.text();

                    Elements urlParse = mDocument.select(".tournament-calendar__name").select("a").eq(i);
                    String Url = urlParse.attr("href");

                    schedules.add(new Schedule(Data,Date,Time,Url,Type));
                }
            }catch(IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            adapter.notifyDataSetChanged();
            Calendar startDate = Calendar.getInstance();
            startDate.set(2019,Calendar.MARCH,1);
//            horizontalCalendar.selectDate(startDate,false);
            horizontalCalendar.goToday(true);
            mProgressDialog.dismiss();
        }
    }

}

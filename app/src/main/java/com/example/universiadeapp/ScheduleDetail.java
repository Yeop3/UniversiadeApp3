package com.example.universiadeapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.universiadeapp.adapters.ScheduleDetailAdapter;
import com.example.universiadeapp.models.Schedule;
import com.example.universiadeapp.models.ScheduleResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetail extends AppCompatActivity {

    private Schedule dataFromIntent;

    private List<ScheduleResult> schedule_d = new ArrayList<>();

    private String TypeDetail, TitleDetail, StatusDetail, PlaceDetail, ScoreDetail;
    private String NumDetail, FlagDetail, PlayerDetail, CountryDetail, ResultDetail;

    private TextView typeDetail, titleDetail, statusDetail, placeDetail, scoreDetail;

    int mElementsSize;

    ScheduleDetailAdapter adapter;
    RecyclerView recyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dataFromIntent = (Schedule) getIntent().getSerializableExtra("detail");

        typeDetail = findViewById(R.id.typeDetail);
        titleDetail = findViewById(R.id.titleDetail);
        statusDetail = findViewById(R.id.statusDetail);
        placeDetail = findViewById(R.id.placeDetail);
        scoreDetail = findViewById(R.id.scoreDetail);

        recyclerView = findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ScheduleDetailAdapter(getApplicationContext(), schedule_d);
        recyclerView.setAdapter(adapter);

        new scheduleParseDetails().execute();
    }

    private class scheduleParseDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Document document = null;

            try {
                document = Jsoup.connect("https://www.championat.com"+dataFromIntent.getUrlEvent()).get();

                Elements typeDetail = document.select(".match-info").select(".match-info__stage");
                TypeDetail = typeDetail.text();

                Elements titleDetail = document.select(".match-info").select(".match-info__title");
                TitleDetail = titleDetail.text();

                Elements statusDetail = document.select(".match-info").select(".match-info__status");
                StatusDetail = statusDetail.text();

                Elements placeDetail = document.select(".match-info").select(".match-info__extra-row").eq(0);
                PlaceDetail = placeDetail.text();

                Elements scoreDetail = document.select(".match-info").select(".match-info__extra-row").eq(1);
                ScoreDetail = scoreDetail.text();

                Elements mElements = document.select(".table-responsive__body").select(".table-responsive__row");

                mElementsSize = mElements.size();
                for (int i = 0; i < mElementsSize; i++){

                    Elements numDetail = document.select(".table-responsive__body").select(".table-responsive__row").select("._num").eq(i);
                    NumDetail = numDetail.text();

                    Elements flagDetail = document.select(".table-responsive__body").select(".table-responsive__row").select("._player").select("._country_flag").eq(i);
                    FlagDetail = flagDetail.attr("src");

                    Elements playerDetail = document.select(".table-responsive__body").select(".table-responsive__row").select("._player").select(".table-item__name").eq(i);
                    PlayerDetail = playerDetail.text();

                    Elements countryDetail = document.select(".table-responsive__body").select(".table-responsive__row").select("._team").select("a").eq(i);
                    CountryDetail = countryDetail.text();

                    Elements resultDetail = document.select(".table-responsive__body").select(".table-responsive__row").select("._result").eq(i);
                    ResultDetail = resultDetail.text();

                    schedule_d.add(new ScheduleResult(NumDetail, FlagDetail, PlayerDetail, CountryDetail, ResultDetail));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            typeDetail.setText(TypeDetail);
            titleDetail.setText(TitleDetail);
            statusDetail.setText(StatusDetail);
            placeDetail.setText(PlaceDetail);
            scoreDetail.setText(ScoreDetail);

            adapter.notifyDataSetChanged();

        }
    }
}

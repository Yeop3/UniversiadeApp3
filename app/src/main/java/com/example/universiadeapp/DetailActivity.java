package com.example.universiadeapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.universiadeapp.models.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    String TitleDetail;
    private News dataFromIntent;
    List<String> Content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_detail);

        dataFromIntent = (News) getIntent().getSerializableExtra("news");

        new newsParseDetails().execute();
    }

    private class newsParseDetails extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            Document document = null;
            try {
                document = Jsoup.connect("https://krsk2019.ru"+dataFromIntent.getUrl()).get();

                Elements titleDetail = document.select(".c-decoration-wrap").select(".header1");
                TitleDetail = titleDetail.text();

                Elements mElements = document.select(".c-decoration-wrap").select(".common-table").select("div");
                int mElementsSize = mElements.size();
                if (mElements.size() > 1) {
                    for (int i = 1; i < mElementsSize; i++) {
                        Elements contentDetail = document.select(".c-decoration-wrap").select(".common-table").select("div").eq(i);
                        Content.add(contentDetail.text());
                    }
                }else {
                    Elements pElements = document.select(".c-decoration-wrap").select(".common-table").select("p");
                    int pElementsSize = pElements.size();
                    for (int i = 1; i < pElementsSize; i++) {
                        Elements contentDetail = document.select(".c-decoration-wrap").select(".common-table").select("p").eq(i);
                        Content.add(contentDetail.text());
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            TextView titleNews = (TextView) findViewById(R.id.news_title_detail);
            TextView contentNews = (TextView) findViewById(R.id.news_content_detail);
            titleNews.setText(TitleDetail);
            StringBuilder builder = new StringBuilder();
            for (String details : Content){
                builder.append(details + "\n"+"\n");
            }
            contentNews.setText(builder.toString());

        }
    }
}
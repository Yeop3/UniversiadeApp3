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

public class DetailActivity extends AppCompatActivity {
    String TitleDetail;
    String ContentDetail;
    private News dataFromIntent;

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

                Elements contentDetail = document.select(".c-decoration-wrap").select(".common-table");
                ContentDetail = contentDetail.text();

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
            contentNews.setText(ContentDetail);
        }
    }
}
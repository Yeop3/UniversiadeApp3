package com.example.universiadeapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.DetailActivity;
import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.NewsAdapter;
import com.example.universiadeapp.models.News;
import com.example.universiadeapp.utils.OnClick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment implements OnClick {
    private ProgressDialog mProgressDialog;
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.list_news);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.mySetOnClickListener(this);
        new newsParse().execute();
        return rootView;
    }

    @Override
    public void setOnClickListener(int position) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("news", newsList.get(position));
        startActivity(intent);
    }

    private class newsParse extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            Document mDocument = null;
            int maxPage = 5;
            try {
                for (int page = 1; page <= maxPage; page++) {
                    if (Locale.getDefault().getLanguage().equals("ru")) {
                        mDocument = Jsoup.connect("https://krsk2019.ru/ru/news_items?page=" + page + "#page-" + page).get();
                    } else {
                        mDocument = Jsoup.connect("https://krsk2019.ru/en/news_items?page=" + page + "#page-" + page).get();
                    }

                    //               mDocument = Jsoup.connect("https://krsk2019.ru/ru/news_items?page=1#page-1").get();


                    Elements mElements = mDocument.select(".news__unit__info-box");
                    int mElementsSize = mElements.size();

                    for (int i = 0; i < mElementsSize; i++) {
                        Elements titleParse = mDocument.select(".header3").eq(i);
                        String Title = titleParse.text();

                        Elements contentParse = mDocument.select(".p1").eq(i);
                        String Content = contentParse.text();

                        Elements dateParse = mDocument.select(".b-square--s").select("time").eq(i);
                        String Date = dateParse.text();

                        Elements urlParse = mDocument.select(".header3").select("a").eq(i);
                        String Url = urlParse.attr("href");

                        newsList.add(new News(Title, Content, Date, Url));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            newsAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }
}

package com.example.universiadeapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

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
    private static ProgressDialog mProgressDialog;
    private static List<News> newsList = new ArrayList<>();
    private static NewsAdapter newsAdapter;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ProgressBar progressBar;
    public static int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.list_news);
        progressBar = (ProgressBar) recyclerView.findViewById(R.id.progress);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.mySetOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

                if (isScrolling  && (currentItems+scrollOutItems == totalItems)){
                    page++;
                    new newsParse().execute();
                    isScrolling = false;
                }

            }
        });
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            Document mDocument = null;
            try {
                    if (Locale.getDefault().getLanguage().equals("ru")) {
                        mDocument = Jsoup.connect("https://krsk2019.ru/ru/news_items?page=" + page + "#page-" + page).get();
                    } else {
                        mDocument = Jsoup.connect("https://krsk2019.ru/en/news_items?page=" + page + "#page-" + page).get();
                    }

                    //               mDocument = Jsoup.connect("https://krsk2019.ru/ru/news_items?page=1#page-1").get();


                    Elements mElements = mDocument.select(".news__unit");
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

                        Elements imageParse = mDocument.select(".news__unit__img-box").select("img").eq(i);
                        String Image = imageParse.attr("src");

                        newsList.add(new News(Title, Content, Date, Url, Image));
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

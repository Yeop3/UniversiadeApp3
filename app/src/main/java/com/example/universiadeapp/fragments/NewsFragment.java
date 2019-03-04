package com.example.universiadeapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class NewsFragment extends Fragment implements OnClick {
    private ProgressBar progressBar;

    private List<News> newsList = new ArrayList<>();
    private Boolean isScrolling = false, loading = false;
    private int page = 1, currentItems, totalItems, scrollOutItems;

    private RecyclerView.LayoutManager layoutManager;
    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        progressBar = rootView.findViewById(R.id.news_progress);

        RecyclerView recyclerView = rootView.findViewById(R.id.news_list);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.mySetOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

                if (!loading && isScrolling && (currentItems + scrollOutItems == totalItems)) {
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
            loading = true;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document mDocument = Jsoup.connect("https://krsk2019.ru/ru/news_items?page=" + page + "#page-" + page).get();

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
            loading = false;
            progressBar.setVisibility(View.GONE);
            newsAdapter.notifyDataSetChanged();
        }
    }
}

package com.example.universiadeapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.Interface.OnClick;
import com.example.universiadeapp.MainActivity;
import com.example.universiadeapp.Models.News;
import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressDialog mProgressDialog;
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new newsParse().execute();
        return null;
    }



    private class newsParse extends AsyncTask<Void, Void, Void> implements OnClick {
        String desc;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mProgressDialog = new ProgressDialog();
            mProgressDialog.setTitle("JsoupParse");
            mProgressDialog.setMessage("Loading...");
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
                    }else {
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

            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result){

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list_news);

            newsAdapter = new NewsAdapter(getContext(), newsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(newsAdapter);
            newsAdapter.mySetOnClickListener(this);
            mProgressDialog.dismiss();
        }

        @Override
        public void setOnClickListener(int position) {
//            Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("news", newsList.get(position));
            startActivity(intent);

        }
    }
}

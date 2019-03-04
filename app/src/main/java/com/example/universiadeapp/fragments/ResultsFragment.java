package com.example.universiadeapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.R;
import com.example.universiadeapp.adapters.NewsAdapter;
import com.example.universiadeapp.adapters.ResultsAdapter;
import com.example.universiadeapp.models.News;
import com.example.universiadeapp.models.Results;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultsFragment extends Fragment {

    private static ProgressDialog mProgressDialog;
    private static List<Results> resultsList = new ArrayList<>();
    private static ResultsAdapter resultsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        new newsParse().execute();
        return rootView;
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
            Document iDocument = null;
            String Gold;
            String Silver;
            String Bronze;
            String Total;
            Elements mElements = null;
            String Country;
            int a = 0;
            int b = 1;
            int c = 2;
            int j = 3;
            try {
                    iDocument = Jsoup.connect("https://www.championat.com/other/_krasnoyarsk2019/tournament/535/standing/").get();
                    mElements = iDocument.select("tbody > tr");

                    mDocument = Jsoup.connect("https://en.wikipedia.org/wiki/2019_Winter_Universiade").get();

                int mElementsSize = mElements.size();

                for (int i = 0; i < mElementsSize; i++) {

                    Elements imageParse = iDocument.select("tbody > tr").select(".medals-table__title").select(".table-item__flag").select("._country_flag").eq(i);
                    String Image = imageParse.attr("src");

                    if (Locale.getDefault().getLanguage().equals("ru")) {
                        Elements countryParse = iDocument.select("tbody > tr").select(".medals-table__title").select(".table-item__name").eq(i);
                        Country = countryParse.text();
                    }else {
                        Elements countryParse = mDocument.select(".plainrowheaders").select("tbody").select("tr > th").select("a").eq(i);
                        Country = countryParse.attr("title");
                    }


                    Elements goldParse = iDocument.select("tbody").select("tr > td").select("._w-10").eq(i+a);
                    Gold = goldParse.text();

                    Elements silverParse = iDocument.select("tbody").select("tr > td").select("._w-10").eq(i + b);
                    Silver = silverParse.text();

                    Elements bronzeParse = iDocument.select("tbody").select("tr > td").select("._w-10").eq(i + c);
                    Bronze = bronzeParse.text();

                    Elements totalParse = iDocument.select("tbody").select("tr > td").select("._w-10").eq(i + j);
                    Total = totalParse.text();

                        a+=3;
                        b+=3;
                        c+=3;
                        j+=3;



                    resultsList.add(new Results(i+1, Image, Country, Gold, Silver, Bronze, Total));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            resultsAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }
}
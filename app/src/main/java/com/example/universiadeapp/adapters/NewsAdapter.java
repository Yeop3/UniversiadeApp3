package com.example.universiadeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.universiadeapp.utils.OnClick;
import com.example.universiadeapp.models.News;
import com.example.universiadeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<News> newsList;
    private OnClick clickListener;


    public void mySetOnClickListener(OnClick clickListener){
        this.clickListener = clickListener;
    }


    public NewsAdapter(Context context, List<News> newsList) {
        this.newsList = newsList;
        this.context = context;

    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        News news = newsList.get(position);

        Picasso.get().load(news.getImage()).into(holder.imageNews);
        holder.textTitle.setText(news.getTitle());
        holder.textContent.setText(news.getContent());
        holder.textDate.setText(news.getDate());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textTitle, textContent, textDate;
        public ImageView imageNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.news_title);
            textContent = (TextView) itemView.findViewById(R.id.news_content);
            textDate = (TextView) itemView.findViewById(R.id.news_date);
            imageNews = itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.setOnClickListener(getAdapterPosition());
                }
            });
        }

    }

}
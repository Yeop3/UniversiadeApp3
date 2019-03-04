package com.example.universiadeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.universiadeapp.R;
import com.example.universiadeapp.models.News;
import com.example.universiadeapp.utils.OnClick;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News> newsList;
    private OnClick clickListener;

    public NewsAdapter(Context context, List<News> newsList) {
        this.newsList = newsList;
        this.context = context;
    }

    public void mySetOnClickListener(OnClick clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textContent, textDate;
        ImageView imageNews;

        ViewHolder(@NonNull View view) {
            super(view);

            textTitle = view.findViewById(R.id.news_item_title);
            textContent = view.findViewById(R.id.news_item_content);
            textDate = view.findViewById(R.id.news_item_date);
            imageNews = view.findViewById(R.id.news_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.setOnClickListener(getAdapterPosition());
                }
            });
        }
    }
}
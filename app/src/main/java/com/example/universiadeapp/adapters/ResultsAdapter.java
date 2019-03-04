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
import com.example.universiadeapp.models.Results;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private Context context;
    private List<Results> resultsList;

    public ResultsAdapter(Context context, List<Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHolder holder, int position) {

        Results results = resultsList.get(position);

        holder.positionView.setText(String.valueOf(results.getPositionInTable()));
        Picasso.get().load(results.getImageCountry()).into(holder.imageView);
        holder.countryView.setText(results.getCountry());
        holder.goldView.setText(results.getGoldMedal());
        holder.silverView.setText(results.getSilverMedal());
        holder.bronzeView.setText(results.getBronzeMedal());
        holder.totalView.setText(results.getTotalMedal());

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView positionView, countryView, goldView, silverView, bronzeView, totalView;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            positionView = itemView.findViewById(R.id.position);
            imageView = itemView.findViewById(R.id.icon_country);
            countryView = itemView.findViewById(R.id.country);
            goldView = itemView.findViewById(R.id.gold);
            silverView = itemView.findViewById(R.id.silver);
            bronzeView = itemView.findViewById(R.id.bronze);
            totalView = itemView.findViewById(R.id.total);

        }
    }
}

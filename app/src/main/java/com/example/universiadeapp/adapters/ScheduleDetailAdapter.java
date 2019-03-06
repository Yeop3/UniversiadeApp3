package com.example.universiadeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.universiadeapp.R;
import com.example.universiadeapp.models.ScheduleResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScheduleDetailAdapter extends RecyclerView.Adapter<ScheduleDetailAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ScheduleResult> schedule_detail;

    public ScheduleDetailAdapter(Context context, List<ScheduleResult> schedule_detail) {
        this.schedule_detail = schedule_detail;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public ScheduleDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schedule_table_score, parent, false);
        return new ScheduleDetailAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ScheduleDetailAdapter.ViewHolder holder, int position) {
        ScheduleResult schedule_d = schedule_detail.get(position);
        holder.numDetail.setText(schedule_d.getNum());
        holder.playerDetail.setText(schedule_d.getPlayer());
        holder.countryDetail.setText(schedule_d.getCountry());
        holder.resultDetail.setText(schedule_d.getResult());
        Picasso.get().load(schedule_d.getFlag()).into(holder.flagDetail);
    }

    @Override
    public int getItemCount() {
        return schedule_detail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView numDetail, playerDetail, countryDetail, resultDetail;
        ImageView flagDetail;

        ViewHolder(View view) {
            super(view);

            numDetail = view.findViewById(R.id.numDetail);
            playerDetail = view.findViewById(R.id.playerDetail);
            countryDetail = view.findViewById(R.id.countryDetail);
            resultDetail = view.findViewById(R.id.resultDetail);
            flagDetail = view.findViewById(R.id.flagDetail);
        }
    }
}

package com.example.universiadeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.universiadeapp.R;
import com.example.universiadeapp.models.Schedule;
import com.example.universiadeapp.utils.OnClick;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Schedule> schedule;
    private OnClick clickListener;

    public ScheduleAdapter(Context context, List<Schedule> schedule) {
        this.schedule = schedule;
        this.inflater = LayoutInflater.from(context);
    }

    public void mySetOnClickListener(OnClick clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schedule_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        Schedule schedule_t = schedule.get(position);
        holder.timeView.setText(schedule_t.getTimeEvent());
        holder.typeView.setText(schedule_t.getTypeEvent());
        holder.dataView.setText(schedule_t.getDataEvent());
    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dataView, typeView, timeView;

        ViewHolder(View view) {
            super(view);

            timeView = view.findViewById(R.id.timeEvent);
            typeView = view.findViewById(R.id.typeEvent);
            dataView = view.findViewById(R.id.dataEvent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.setOnClickListener(getAdapterPosition());
                }
            });
        }
    }
}

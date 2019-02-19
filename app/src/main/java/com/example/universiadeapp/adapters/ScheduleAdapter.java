package com.example.universiadeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.universiadeapp.Fragments.ScheduleFragment;
import com.example.universiadeapp.Models.Schedule;
import com.example.universiadeapp.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Schedule> schedules;

    public ScheduleAdapter(Context context, List<Schedule> schedules){

        this.schedules = schedules;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = inflater.inflate(R.layout.fragment_schedule, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position){

        Schedule schedule = schedules.get(position);
        holder.nameView.setText(schedule.getNameEvent());
        holder.placeView.setText(schedule.getPlaceEvent());
        holder.timeView.setText(schedule.getTimeEvent());
    }

    @Override
    public int getItemCount(){
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView, placeView, timeView;
        ViewHolder(View view){

            super(view);
            nameView = (TextView)view.findViewById(R.id.nameEvent);
            placeView = (TextView)view.findViewById(R.id.placeEvent);
            timeView = (TextView)view.findViewById(R.id.timeEvent);

        }
    }
}

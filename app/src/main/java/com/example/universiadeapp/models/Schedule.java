package com.example.universiadeapp.models;

import android.graphics.drawable.Drawable;

public class Schedule {

    private String nameEvent;
    private String placeEvent;
    private String timeEvent;
//    private int imageEvent;

    public Schedule(String nameEvent, String placeEvent, String timeEvent){

        this.nameEvent = nameEvent;
        this.placeEvent = placeEvent;
        this.timeEvent = timeEvent;
//        this.imageEvent = imageEvent;

    }

    public String getNameEvent(){
        return this.nameEvent;
    }

    public void setNameEvent(String nameEvent){
        this.nameEvent = nameEvent;
    }

    public String getPlaceEvent(){
        return  this.placeEvent;
    }

    public void setPlaceEvent(String placeEvent){
        this.placeEvent = placeEvent;
    }

    public String getTimeEvent(){
        return this.timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

//    public Drawable getImageEvent() {
//        return imageEvent;
//    }
//
//    public void setImageEvent(int imageEvent) {
//        this.imageEvent = imageEvent;
//    }
}
package com.example.universiadeapp.models;


import java.io.Serializable;

public class Schedule implements Serializable {

    private String dataEvent;
    private int dateEvent;
    private String typeEvent;
    private String timeEvent;
    private String urlEvent;

    public Schedule(String dataEvent, int dateEvent, String timeEvent, String urlEvent, String typeEvent){

        this.dataEvent = dataEvent;
        this.timeEvent = timeEvent;
        this.dateEvent = dateEvent;
        this.urlEvent = urlEvent;
        this.typeEvent = typeEvent;
    }

    public String getDataEvent() {
        return dataEvent;
    }

    public void setDataEvent(String dataEvent) {
        this.dataEvent = dataEvent;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public int getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(int dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getUrlEvent() {
        return urlEvent;
    }

    public void setUrlEvent(String urlEvent) {
        this.urlEvent = urlEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }
}
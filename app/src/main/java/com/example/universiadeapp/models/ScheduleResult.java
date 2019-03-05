package com.example.universiadeapp.models;

public class ScheduleResult {

    private String num;
    private String flag;
    private String player;
    private String country;
    private String result;

    public ScheduleResult(String num, String flag, String player, String country, String result) {
        this.num = num;
        this.flag = flag;
        this.player = player;
        this.country = country;
        this.result = result;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

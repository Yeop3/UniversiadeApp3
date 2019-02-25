package com.example.universiadeapp.models;

import java.io.Serializable;

public class News implements Serializable {

    public String title;
    public String content;
    public String date;
    public String url;


    public News(){

    }

    public News(String title, String content, String date, String url){

        this.content = content;
        this.title = title;
        this.date = date;
        this.url = url;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

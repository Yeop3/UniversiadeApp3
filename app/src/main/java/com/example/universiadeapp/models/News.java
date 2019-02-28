package com.example.universiadeapp.models;

import java.io.Serializable;

public class News implements Serializable {

    public String title;
    public String content;
    public String date;
    public String url;
    public String image;


    public News(){

    }

    public News(String title, String content, String date, String url, String image){

        this.content = content;
        this.title = title;
        this.date = date;
        this.url = url;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

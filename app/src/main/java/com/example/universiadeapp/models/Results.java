package com.example.universiadeapp.models;

public class Results {

    private int positionInTable;
    private String imageCountry;
    private String country;
    private String goldMedal;
    private String silverMedal;
    private String bronzeMedal;
    private String totalMedal;

    public Results(int positionInTable, String imageCountry, String country, String goldMedal, String silverMedal, String bronzeMedal, String totalMedal) {
        this.positionInTable = positionInTable;
        this.imageCountry = imageCountry;
        this.country = country;
        this.goldMedal = goldMedal;
        this.silverMedal = silverMedal;
        this.bronzeMedal = bronzeMedal;
        this.totalMedal = totalMedal;
    }

    public int getPositionInTable() {
        return positionInTable;
    }

    public void setPositionInTable(int positionInTable) {
        this.positionInTable = positionInTable;
    }

    public String getImageCountry() {
        return imageCountry;
    }

    public void setImageCountry(String imageCountry) {
        this.imageCountry = imageCountry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGoldMedal() {
        return goldMedal;
    }

    public void setGoldMedal(String goldMedal) {
        this.goldMedal = goldMedal;
    }

    public String getSilverMedal() {
        return silverMedal;
    }

    public void setSilverMedal(String silverMedal) {
        this.silverMedal = silverMedal;
    }

    public String getBronzeMedal() {
        return bronzeMedal;
    }

    public void setBronzeMedal(String bronzeMedal) {
        this.bronzeMedal = bronzeMedal;
    }

    public String getTotalMedal() {
        return totalMedal;
    }

    public void setTotalMedal(String totalMedal) {
        this.totalMedal = totalMedal;
    }
}

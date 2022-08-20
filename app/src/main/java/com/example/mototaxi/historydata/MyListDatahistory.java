package com.example.mototaxi.historydata;

public class MyListDatahistory {
    private String date;
    private String days;
    private  String textmodel;
    private  String rate;
    private  String textaddress;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTextmodel() {
        return textmodel;
    }

    public void setTextmodel(String textmodel) {
        this.textmodel = textmodel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTextaddress() {
        return textaddress;
    }

    public void setTextaddress(String textaddress) {
        this.textaddress = textaddress;
    }

    public MyListDatahistory(String date,String days,String textmodel, String rate,String textaddress) {
        this.date = date;
        this.days = days;
        this.textmodel = textmodel;
        this.rate = rate;
        this.textaddress = textaddress;
    }
}

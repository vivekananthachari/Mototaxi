package com.example.mototaxi.fual;

public class MyListDatafualnew {
    private  String state;
    private  String petrol;
    private  String diesel;
    private  String date;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPetrol() {
        return petrol;
    }

    public void setPetrol(String petrol) {
        this.petrol = petrol;
    }

    public String getDiesel() {
        return diesel;
    }

    public void setDiesel(String diesel) {
        this.diesel = diesel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyListDatafualnew(String state, String petrol, String diesel, String date) {
        this.state = state;
        this.petrol = petrol;
        this.diesel = diesel;
        this.date=date;

    }

}

package com.example.mototaxi.fual;

public class MyListDatafual {
    private  String model;
    private  String amount;
    private  String updown;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }

    public MyListDatafual(String model,String amount,String updown) {
        this.model = model;
        this.amount = amount;
        this.updown = updown;

    }

}

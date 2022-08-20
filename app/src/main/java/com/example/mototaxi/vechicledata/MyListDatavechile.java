package com.example.mototaxi.vechicledata;

public class MyListDatavechile {
    private  String model;
    private  String Number;
    private  String Name;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public MyListDatavechile(String model,String name,String number) {
        this.model = model;
        this.Name = name;
        this.Number = number;
    }
}

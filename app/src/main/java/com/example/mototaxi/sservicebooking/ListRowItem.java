package com.example.mototaxi.sservicebooking;

import java.io.Serializable;

public class ListRowItem implements Serializable {
    String carrier;

    public String getCarrier(){
        return carrier;
    }


    public void setCarrier(String ba_carrier){
        carrier = ba_carrier;
    }
}
package com.example.mototaxi.profiledata;

public class MyListData {
    private String auuid;
    private String label;
    private String address;
    private  String lat;
    private String longs;

    public String getLongs() {
        return longs;
    }

    public void setLongs(String longs) {
        this.longs = longs;
    }

    public String getAuuid() {
        return auuid;
    }

    public void setAuuid(String auuid) {
        this.auuid = auuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public MyListData (String auuid,String label,String address,String lat,String Long){
        this.auuid=auuid;
        this.label=label;
        this.address=address;
        this.lat=lat;
        this.longs=Long;
    }
}

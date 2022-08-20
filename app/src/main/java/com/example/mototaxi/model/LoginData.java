package com.example.mototaxi.model;

public class LoginData {
    private String mobilenumber;

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    private String buuid;
    private String type;
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }



    public String getBuuid() {
        return buuid;
    }

    public void setBuuid(String buuid) {
        this.buuid = buuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginData(String mobilenumber, String buuid, String type) {
        this.mobilenumber=mobilenumber;
        this.buuid=buuid;
        this.type = type;

    }


}

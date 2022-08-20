package com.example.mototaxi.dashboaddata;

public class CourseModel {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private String course_name;
    private int imgid;
    private  String count;
    private  String bacgroundcolor;

    public CourseModel(String course_name, int imgid, String count,String bacgroundcolor) {
        this.course_name = course_name;
        this.imgid = imgid;
        this.count=count;
        this.bacgroundcolor=bacgroundcolor;
    }

    public String getBacgroundcolor(){
        return bacgroundcolor;
    }
    public  void setBacgroundcolor(String bacgroundcolor){
        this.bacgroundcolor=bacgroundcolor;
    }
    public  String getCount(){
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}

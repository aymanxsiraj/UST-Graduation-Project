package com.example.graduationproject;

public class WorkDays {

    private String id;
    private String day;
    private String date;

    private String time;

    public WorkDays() {
    }

    public WorkDays(String id,String day, String date, String time) {
        this.id = id;
        this.day = day;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

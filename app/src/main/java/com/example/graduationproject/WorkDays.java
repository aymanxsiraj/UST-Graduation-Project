package com.example.graduationproject;

public class WorkDays {

    private String id;
    private String day;
    private String date;

    private String time;
    private String price;

    public WorkDays() {
    }

    public WorkDays(String id,String day, String date, String time, String price) {
        this.id = id;
        this.day = day;
        this.date = date;
        this.time = time;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

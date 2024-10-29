package com.example.graduationproject;

public class PatientReport {
    private String date;
    private String name;
    private String phone;
    private String age;
    private String description;

    public PatientReport() {
    }

    public PatientReport(String date, String name, String phone, String age, String description) {
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

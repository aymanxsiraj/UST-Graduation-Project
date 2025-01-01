package com.example.graduationproject;

public class PatientReport {
    private String date;
    private String name;
    private String phone;
    private String age;
    private String description;

    private String id;
    private String gender;

    public PatientReport() {
    }

    public PatientReport(String date, String name, String phone, String age, String description, String gender,String id) {
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.description = description;
        this.gender = gender;
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

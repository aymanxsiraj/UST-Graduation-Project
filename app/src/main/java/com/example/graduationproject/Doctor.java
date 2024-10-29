package com.example.graduationproject;

public class Doctor {

    private String UID;
    private String doctorName;
    private String doctorEmail;
    private String doctorPhone;
    private String doctorPassword;
    private String doctorSpecialist;
    private String Time;

    public Doctor() {
    }



    public Doctor(String UID,String doctorName, String doctorEmail, String doctorPhone, String doctorPassword, String doctorSpecialist) {
        this.UID = UID;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.doctorPhone = doctorPhone;
        this.doctorPassword = doctorPassword;
        this.doctorSpecialist = doctorSpecialist;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getDoctorSpecialist() {
        return doctorSpecialist;
    }

    public void setDoctorSpecialist(String doctorSpecialist) {
        this.doctorSpecialist = doctorSpecialist;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

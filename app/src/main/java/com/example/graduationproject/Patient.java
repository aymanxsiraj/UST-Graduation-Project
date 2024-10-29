package com.example.graduationproject;

public class Patient {

    private String UID;
    private String PatientName;
    private String PatientEmail;
    private String PatientPhone;
    private String PatientPassword;

    public Patient() {

    }



    public Patient(String patientEmail, String patientPassword) {
        PatientEmail = patientEmail;
        PatientPassword = patientPassword;
    }

    public Patient(String UID,String patientName, String patientEmail, String patientPhone, String patientPassword) {
        this.UID = UID;
        PatientName = patientName;
        PatientEmail = patientEmail;
        PatientPhone = patientPhone;
        PatientPassword = patientPassword;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPatientEmail() {
        return PatientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        PatientEmail = patientEmail;
    }

    public String getPatientPhone() {
        return PatientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        PatientPhone = patientPhone;
    }

    public String getPatientPassword() {
        return PatientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        PatientPassword = patientPassword;
    }
}

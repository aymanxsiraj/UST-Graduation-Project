package com.example.graduationproject;

public class Confirmation {
    private String UID;
    private String email;
    private String confirm;

    public Confirmation() {
    }

    public Confirmation(String UID,String email, String confirm) {
        this.UID = UID;
        this.email = email;
        this.confirm = confirm;

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}

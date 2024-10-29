package com.example.graduationproject;

public class AutoLogin {
    private String uid;
    private String type;

    public AutoLogin(String uid, String type) {
        this.uid = uid;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.example.thienbao.myapplication.mClass;

import android.widget.TextView;

public class feddback_ad {
    private String User, Mes;

    public feddback_ad(String user, String mes) {
        User = user;
        Mes = mes;
    }

    public String getUser() {
        return User;
    }

    public String getMes() {
        return Mes;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setMes(String mes) {
        Mes = mes;
    }
}

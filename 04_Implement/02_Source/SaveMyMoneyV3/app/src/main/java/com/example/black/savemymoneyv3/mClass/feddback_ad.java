package com.example.black.savemymoneyv3.mClass;

public class feddback_ad {
    private String User, Mes;
    private int Sao;

    public feddback_ad(String user, String mes, int sao) {
        User = user;
        Mes = mes;
        Sao = sao;
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

    public int getSao() {
        return Sao;
    }
}

package com.example.black.savemymoneyv3.mClass;

import java.util.Calendar;

public class GiaoDich {
    int id_gd, id_vi , loaigd;
    String ghichu;
    Calendar calendar;
    Long money;

    public GiaoDich(int id_gd, int id_vi, int loaigd, String ghichu, Calendar calendar, Long money) {
        this.id_gd = id_gd;
        this.id_vi = id_vi;
        this.loaigd = loaigd;
        this.ghichu = ghichu;
        this.calendar = calendar;
        this.money = money;
    }

    public int getId_gd() {
        return id_gd;
    }

    public void setId_gd(int id_gd) {
        this.id_gd = id_gd;
    }

    public int getId_vi() {
        return id_vi;
    }

    public void setId_vi(int id_vi) {
        this.id_vi = id_vi;
    }

    public int getLoaigd() {
        return loaigd;
    }

    public void setLoaigd(int loaigd) {
        this.loaigd = loaigd;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}

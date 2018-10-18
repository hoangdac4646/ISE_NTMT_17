package com.example.black.savemymoneyv3.mClass;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Calendar;

public class KhoangChiTieu implements Serializable {
    int id;
    int icon;
    String Name;
    Long Money;
    Calendar date;
    String taikhoang;

    public KhoangChiTieu(int id, int icon, String name, Long money, Calendar date, String taikhoang) {
        this.id = id;
        this.icon = icon;
        Name = name;
        Money = money;
        this.date = date;
        this.taikhoang = taikhoang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getTaikhoang() {
        return taikhoang;
    }

    public void setTaikhoang(String taikhoang) {
        this.taikhoang = taikhoang;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getMoney() {
        return Money;
    }

    public void setMoney(Long money) {
        Money = money;
    }

    public int getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }
}

package com.example.black.savemymoneyv3.mClass;

import java.io.Serializable;
import java.util.Calendar;

public class Borrows implements Serializable {
    private int id;
    private Long Money;
    private int Loai;
    private String Name;
    private Calendar ngay;
    private String taikhoan;


    public Borrows(int id, Long money, int loai, String name, Calendar ngay, String taikhoan) {
        this.id = id;
        Money = money;
        Loai = loai;
        Name = name;
        this.ngay = ngay;
        this.taikhoan = taikhoan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getMoney() {
        return Money;
    }

    public void setMoney(Long money) {
        Money = money;
    }

    public int getLoai() {
        return Loai;
    }

    public void setLoai(int loai) {
        Loai = loai;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Calendar getNgay() {
        return ngay;
    }

    public void setNgay(Calendar ngay) {
        this.ngay = ngay;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }
}

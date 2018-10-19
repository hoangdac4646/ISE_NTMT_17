package com.example.black.savemymoneyv3.DangNhap;

import java.io.Serializable;

public class TaiKhoan implements  Serializable{
    private String Name,Pass,sodt,ViTri,HoTen;

    public TaiKhoan(String name, String pass, String sodt, String ViTri, String Hoten) {
        Name = name;
        this.ViTri = ViTri;
        Pass = pass;
        this.sodt = sodt;
        this.HoTen = Hoten;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public void setViTri(String viTri) {
        ViTri = viTri;
    }

    public String getName() {
        return Name;
    }

    public String getPass() {
        return Pass;
    }

    public String getSodt() {
        return sodt;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getViTri() {
        return ViTri;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
}


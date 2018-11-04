package com.example.black.savemymoneyv3.mClass;

public class NgoaiTe {
    private String Ten,MieuTa;
    private Double TiGiaSoVoiDola,TiGiaDola;
    private int Hinh;

    public NgoaiTe(String ten, String mieuTa, Double tiGia, Double TiGiaDola, int hinh) {
        Ten = ten;
        MieuTa = mieuTa;
        TiGiaSoVoiDola = tiGia;
        Hinh = hinh;
        this.TiGiaDola = TiGiaDola;
    }

    public Double getTiGiaSoVoiDola() {
        return TiGiaSoVoiDola;
    }

    public Double getTiGiaDola() {
        return TiGiaDola;
    }

    public String getMieuTa() {
        return MieuTa;
    }

    public int  getHinh() {
        return Hinh;
    }

    public String getTen() {
        return Ten;
    }

    public Double getTiGia() {
        return TiGiaSoVoiDola;
    }


}

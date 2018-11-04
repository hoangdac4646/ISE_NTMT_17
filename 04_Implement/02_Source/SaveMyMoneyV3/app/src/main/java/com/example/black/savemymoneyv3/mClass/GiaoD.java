package com.example.black.savemymoneyv3.mClass;

public class GiaoD {
    private int TienThu=0,TienChi=0;
    private String MaVi;

    public GiaoD(int TienThu, int TienChi, String MaVi) {
        this.TienThu = TienThu;
        this.TienChi =TienChi;

        this.MaVi = MaVi;
    }

    public void setTienThu(int tienThu) {
        TienThu += tienThu;
    }

    public void setTienChi(int tienChi) {
        TienChi += tienChi;
    }



    public void setMavi(String mavi) {
        MaVi = mavi;
    }

    public String getMavi() {
        return MaVi;
    }

    public int getTienThu() {
        return TienThu;
    }

    public int getThienChi() {
        return TienChi;
    }
}

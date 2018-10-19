package com.example.black.savemymoneyv3.mClass;

import java.io.Serializable;
import java.util.Calendar;

public class DuDinh implements Serializable {
    private  int id_gd;
    private Long kinhPhi;
    private Calendar ngayKetThuc;
    private String ghiChu;
    private String taikhoan;

    public DuDinh( int id_gd, Long kinhPhi, Calendar ngayKetThuc, String ghiChu, String taikhoan) {
        this.id_gd = id_gd;
        this.kinhPhi = kinhPhi;
        this.ngayKetThuc = ngayKetThuc;
        this.ghiChu = ghiChu;
        this.taikhoan = taikhoan;
    }

    public int getId_gd() {
        return id_gd;
    }

    public void setId_gd(int id_gd) {
        this.id_gd = id_gd;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public Long getKinhPhi() {
        return kinhPhi;
    }

    public void setKinhPhi(Long kinhPhi) {
        this.kinhPhi = kinhPhi;
    }

    public Calendar getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Calendar ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}

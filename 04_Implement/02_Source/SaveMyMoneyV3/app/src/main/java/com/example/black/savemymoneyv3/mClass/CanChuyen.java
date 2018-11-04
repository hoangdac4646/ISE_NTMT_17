package com.example.black.savemymoneyv3.mClass;

import java.io.Serializable;

public class CanChuyen implements Serializable {
    private String TenVi,MaVi;

    public CanChuyen(String tenVi, String maVi) {
        TenVi = tenVi;
        MaVi = maVi;
    }

    public String getTenVi() {
        return TenVi;
    }

    public String getMaVi() {
        return MaVi;
    }
}

package com.example.black.savemymoneyv3.mClass;

public class ThuChi {
    private String Thu,Chi;
    private int Anh;

    public ThuChi(String thu, String chi, int anh) {
        Thu = thu;
        Chi = chi;
        Anh = anh;
    }

    public String getThu() {
        return Thu;
    }

    public String getChi() {
        return Chi;
    }

    public int getAnh() {
        return Anh;
    }
}

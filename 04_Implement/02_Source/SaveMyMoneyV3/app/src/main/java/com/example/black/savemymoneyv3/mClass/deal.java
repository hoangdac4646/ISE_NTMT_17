package com.example.black.savemymoneyv3.mClass;

public class deal {
    String Url_anh,Url_Link;
    int id;

    public deal(int id,String url_anh, String url_Link) {
        Url_anh = url_anh;
        Url_Link = url_Link;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUrl_anh() {
        return Url_anh;
    }

    public String getUrl_Link() {
        return Url_Link;
    }
}

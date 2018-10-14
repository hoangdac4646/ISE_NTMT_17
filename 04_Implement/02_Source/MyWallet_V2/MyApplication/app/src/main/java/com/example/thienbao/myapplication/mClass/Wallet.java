package com.example.thienbao.myapplication.mClass;

import java.io.Serializable;
import java.util.Calendar;

public class Wallet implements Serializable {
    private int idUser;
    private int id;
    private String Wallet_name;
    private int category_wallet;
    private long Money;
    private Calendar init_time;

    public Wallet(int idUser,int id,String wallet_name,int category_wallet, long money, Calendar init_time) {
        this.idUser  = idUser;
        this.id = id;
        Wallet_name = wallet_name;
        this.category_wallet = category_wallet;
        Money = money;

        this.init_time = init_time;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_wallet() {
        return category_wallet;
    }

    public void setCategory_wallet(int category_wallet) {
        this.category_wallet = category_wallet;
    }

    public String getWallet_name() {
        return Wallet_name;
    }

    public void setWallet_name(String wallet_name) {
        Wallet_name = wallet_name;
    }

    public long getMoney() {
        return Money;
    }

    public void setMoney(long money) {
        Money = money;
    }

    public Calendar getInit_time() {
        return init_time;
    }

    public void setInit_time(Calendar init_time) {
        this.init_time = init_time;
    }
}

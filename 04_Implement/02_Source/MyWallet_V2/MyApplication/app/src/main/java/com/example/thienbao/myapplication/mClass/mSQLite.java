package com.example.thienbao.myapplication.mClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SAVEMYMONEY.sql";


    SQLiteDatabase database;
    public mSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void QueryData(String sql){
        database = getWritableDatabase();
        database.execSQL(sql);

    }

    public Cursor GetData(String sql){
        database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Wallet(id INTEGER PRIMARY KEY AUTOINCREMENT, name NVARCHAR(255), category INTEGER, money MONEY, timeinit NVARCHAR(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

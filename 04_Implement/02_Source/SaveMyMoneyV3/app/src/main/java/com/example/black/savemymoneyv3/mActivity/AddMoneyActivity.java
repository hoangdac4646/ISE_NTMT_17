package com.example.black.savemymoneyv3.mActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTienNap, edt_ngayNap, edt_ghichu_AW;
    Button btn_XacNhan;
    Toolbar mtoolbar;

    TextView ten, tien, ngaytao;

    KhoangChiTieu wallet;
    int loaiGD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        InitWork();
        btn_XacNhan.setOnClickListener(this);
    }

    private void InitWork(){
        edtTienNap      = (EditText) findViewById(R.id.edt_TienNap);
        edt_ngayNap     = (EditText) findViewById(R.id.edt_ngayNap);
        edt_ghichu_AW   = (EditText) findViewById(R.id.edt_ghichu_AW);
        btn_XacNhan     = (Button) findViewById(R.id.btn_xacnhan_AW);

        ten             = (TextView) findViewById(R.id.txtName_AW);
        tien            = (TextView) findViewById(R.id.txtMoney_AW);
        ngaytao         = (TextView) findViewById(R.id.txtDate_AW);
        mtoolbar        = (Toolbar) findViewById(R.id.mtoolbar_AW);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        wallet = (KhoangChiTieu) intent.getSerializableExtra("wallet");
        loaiGD = intent.getIntExtra("loaigd", 0);
        ten.setText(wallet.getName());
        tien.append(wallet.getMoney() + "");
        Calendar calendar = wallet.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ngaytao.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_xacnhan_AW){
            //do something here
        }
    }
}

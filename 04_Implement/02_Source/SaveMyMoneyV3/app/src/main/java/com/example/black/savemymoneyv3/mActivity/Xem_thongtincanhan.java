package com.example.black.savemymoneyv3.mActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.TaiKhoan;
import com.example.black.savemymoneyv3.R;

import java.util.HashMap;
import java.util.Map;

public class Xem_thongtincanhan extends AppCompatActivity {
    private EditText taikhoan,matkhau,ten,sodt;
    private Button DongY;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemthongtin);
        Init();
        Intent intent = getIntent();
        TaiKhoan a = (TaiKhoan) intent.getSerializableExtra("TaiKhoan");
        taikhoan.setText(a.getName());
        matkhau.setText(a.getPass());
        ten.setText(a.getHoTen());
        sodt.setText(a.getSodt());
        DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });

    }

    private void Init(){
        taikhoan = findViewById(R.id.edt_CHuser1);
        matkhau = findViewById(R.id.edt_CHpass1);
        ten = findViewById(R.id.edt_CHten1);
        sodt = findViewById(R.id.edt_CHsdt1);
        DongY = findViewById(R.id.btn_CHOke1);

    }

}

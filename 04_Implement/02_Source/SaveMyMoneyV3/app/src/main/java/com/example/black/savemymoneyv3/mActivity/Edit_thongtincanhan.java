package com.example.black.savemymoneyv3.mActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class Edit_thongtincanhan extends AppCompatActivity {
    private EditText taikhoan,matkhau,ten,sodt;
    private Button DongY,Huy;
    private String url  ="http://ludicrous-disaster.hostingerapp.com/UpdateThongTin.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chinhsuatt_main2);
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
                if(matkhau.getText().length()==0 || ten.getText().length()==0 || sodt.getText().length()==0){
                    Toast.makeText(Edit_thongtincanhan.this, "Không được để trống thông tin !", Toast.LENGTH_SHORT).show();
                }else{
                    Update(url);
                }
            }
        });
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Init(){
        taikhoan = findViewById(R.id.edt_CHuser);
        matkhau = findViewById(R.id.edt_CHpass);
        ten = findViewById(R.id.edt_CHten);
        sodt = findViewById(R.id.edt_CHsdt);
        DongY = findViewById(R.id.btn_CHOke);
        Huy = findViewById(R.id.btn_CHcannel);
    }
    private void Update(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.toString().trim().indexOf("Success")!=-1){
                    Toast.makeText(Edit_thongtincanhan.this, response, Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",taikhoan.getText().toString().trim());
                map.put("userpass",matkhau.getText().toString().trim());
                map.put("Sodt",sodt.getText().toString().trim());
                map.put("Hoten",ten.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}

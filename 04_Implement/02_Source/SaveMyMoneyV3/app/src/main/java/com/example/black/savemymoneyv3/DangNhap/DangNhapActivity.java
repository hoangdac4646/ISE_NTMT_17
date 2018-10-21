package com.example.black.savemymoneyv3.DangNhap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {

    private TextView Dangki;
    private Button DangNhap;
    private EditText TaiKhoan,MatKhau,Hoten;
    //list tai khoan doc tu service
    private  static   List<TaiKhoan> list_tk;
    private int KiemTra=0;
    private ProgressDialog progress;
    public static TaiKhoan user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap_activity);
        Init();
        int n =list_tk.size();
        final String url = "https://ludicrous-disaster.000webhostapp.com/Login.php";
         n = list_tk.size();
        Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_DangKi = new Intent(DangNhapActivity.this,DangKiActivity.class);
                startActivity(intent_DangKi);
            }
        });
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = ProgressDialog.show(DangNhapActivity.this, "", "Loading...", true);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ReadTaiKhoan(url);
                    }
                });
            }
        });

    }
    public void Init(){
        list_tk = new ArrayList<TaiKhoan>();
        Dangki = findViewById(R.id.tv_dangki);
        DangNhap = findViewById(R.id.btn_dangnhap);
        TaiKhoan = findViewById(R.id.edt_name);
        MatKhau = findViewById(R.id.edt_pass);



    }
    private void ReadTaiKhoan(String url){
        progress.show();
       final  List<TaiKhoan> a =new ArrayList<TaiKhoan>();
         final String Dem= new String();
         //Request lay du lieu tu database ve
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String TenTk="",Matkhau="",Sodt="",ViTri="-1",Hoten="";
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                   TenTk = object.getString("Name");
                     Matkhau = object.getString("MatKhau");
                     Sodt = object.getString("SoDt");
                     ViTri = object.getString("ViTri");
                     Hoten = object.getString("HoTen");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(ViTri.equals("1")){
                    progress.dismiss();
                    startActivity(new Intent(DangNhapActivity.this,AdminActivity.class));
                }else if(ViTri.equals("0")){
                    ///Dang nhap thanh cong
                    progress.dismiss();
                    Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                    //Gui goi tin tai khoan qua acti moi
                    TaiKhoan a = new TaiKhoan(TenTk,Matkhau,Sodt,ViTri,Hoten);
                    user = a;
                    intent.putExtra("TaiKhoan",a);
                    startActivity(intent);
                    //
                }
                else{
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công !",Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.toString(),Toast.LENGTH_SHORT).show();
                progress.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",TaiKhoan.getText().toString());
                map.put("userpass",MatKhau.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}

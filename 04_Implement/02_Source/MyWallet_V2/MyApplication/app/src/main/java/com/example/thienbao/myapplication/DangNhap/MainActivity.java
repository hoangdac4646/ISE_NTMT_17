package com.example.thienbao.myapplication.DangNhap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mActivity.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView Dangki;
    private Button DangNhap;
    private EditText TaiKhoan,MatKhau,Hoten;
    //list tai khoan doc tu service
    private  static   List<TaiKhoan> list_tk;
    private int KiemTra=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        int n =list_tk.size();
        final String url = "https://ludicrous-disaster.000webhostapp.com/Login.php";
         n = list_tk.size();
        Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_DangKi = new Intent(MainActivity.this,DangKiActivity.class);
                startActivity(intent_DangKi);
            }
        });
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReadTaiKhoan(url);
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
                     Hoten = object.getString("Hoten");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(ViTri.equals("1")){
                    startActivity(new Intent(MainActivity.this,AdminActivity.class));
                }else if(ViTri.equals("0")){
                    ///Dang nhap thanh cong
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    //Gui goi tin tai khoan qua acti moi
                    TaiKhoan a = new TaiKhoan(TenTk,Matkhau,Sodt,ViTri,Hoten);
                    intent.putExtra("TaiKhoan",a);
                    startActivity(intent);
                    //
                }
                else{
                    Toast.makeText(getApplicationContext(),"Đăng nhập không thành công !",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.toString(),Toast.LENGTH_SHORT).show();
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

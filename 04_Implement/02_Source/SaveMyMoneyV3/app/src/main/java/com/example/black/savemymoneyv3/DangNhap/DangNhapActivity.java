package com.example.black.savemymoneyv3.DangNhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

//

public class DangNhapActivity extends AppCompatActivity {

    private TextView Dangki;
    private Button DangNhap;
    private EditText TaiKhoan,MatKhau;
    private CheckBox checkBox;
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
        final String url = "http://ludicrous-disaster.hostingerapp.com/Login.php";
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

                if(checkBox.isChecked()){
                    Boolean Checked = checkBox.isChecked();
                    SharedPreferences.Editor editor = StartActivity.mPrefs.edit();
                    editor.putString("pref_name",TaiKhoan.getText().toString());
                    editor.putString("pref_pass",MatKhau.getText().toString());
                    editor.putBoolean("pref_check",Checked);
                    editor.apply();
                }else{
                    StartActivity.mPrefs.edit().clear().apply();
                }
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ReadTaiKhoan(url,DangNhapActivity.this,TaiKhoan.getText().toString(),MatKhau.getText().toString());
                        TaiKhoan.getText().clear();
                        MatKhau.getText().clear();
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
        checkBox = findViewById(R.id.check_remember);


    }
    public static  void ReadTaiKhoan(String url, final Context context,final String TaiKhoan1,final String MatKhau1){
       final  ProgressDialog progress1 = ProgressDialog.show(context, "", "Loading...", true);
        progress1.show();
       final  List<TaiKhoan> a =new ArrayList<TaiKhoan>();
         final String Dem= new String();
         //Request lay du lieu tu database ve
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                    progress1.dismiss();
                    context.startActivity(new Intent(context,AdminActivity.class));
                }else if(ViTri.equals("0")){
                    ///Dang nhap thanh cong
                    progress1.dismiss();
                    Intent intent = new Intent(context,MainActivity.class);
                    //Gui goi tin tai khoan qua acti moi
                    TaiKhoan a = new TaiKhoan(TenTk,Matkhau,Sodt,ViTri,Hoten);
                    user = a;
                    intent.putExtra("TaiKhoan",a);
                    context.startActivity(intent);
                    //
                }
                else{
                    Toast.makeText(context.getApplicationContext(),"Đăng nhập không thành công !",Toast.LENGTH_SHORT).show();
                    progress1.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error :    "+error.toString(),Toast.LENGTH_SHORT).show();
                progress1.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",TaiKhoan1);
                map.put("userpass",MatKhau1);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}

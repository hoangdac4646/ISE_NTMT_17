package com.example.black.savemymoneyv3.DangNhap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.black.savemymoneyv3.R;

import java.util.HashMap;
import java.util.Map;

public class DangKiActivity extends AppCompatActivity {
    private EditText Name,Pass1,Pass2,Sodt,Hoten;
    private Button DongY,Huy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        Init();

        DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Dem=0;
                for(int i=0;i<Name.length();i++){
                    if(Name.getText().charAt(i) <=47 || (Name.getText().charAt(i)>=58 && Name.getText().charAt(i) <=64) ||
                            Name.getText().charAt(i) >=91 && Name.getText().charAt(i)<=96 || Name.getText().charAt(i)>=123 ){
                        Dem=1;
                        break;
                    }
                }

                if (Name.length() == 0 || Pass1.length() == 0 || Pass2.length() == 0 || Hoten.length() ==0) {
                        Toast.makeText(getApplicationContext(), "Không được để trống !", Toast.LENGTH_SHORT).show();
                } else if(Pass1.getText().toString().trim().equals(Pass2.getText().toString().trim())) {
                    if(Pass1.length() <3 ||  Pass1.length()>20) {
                        Toast.makeText(getApplicationContext(), "Mật khẩu lớn hơn 3 kí tự và nhỏ hơn 20 !", Toast.LENGTH_SHORT).show();
                    }
                    else if(Name.length() <5 || Name.length() >50){
                        Toast.makeText(getApplicationContext(), "Tài khoản lớn hơn 5 kí tự và nhỏ hơn 50 !", Toast.LENGTH_SHORT).show();
                    }
                    else if(Name.getText().toString().trim().indexOf(" ")!=-1){
                        Toast.makeText(DangKiActivity.this, "Tài khoản không được chứa dấu khoảng trắng !", Toast.LENGTH_SHORT).show();
                    }else if(Dem==1){
                        Toast.makeText(DangKiActivity.this, "Tài khoản không được chứa kí tự đăc biệt !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String url = "http://ludicrous-disaster.hostingerapp.com/DangKi.php";
                        DangKi(url);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Mật khẩu không đúng ! ", Toast.LENGTH_SHORT).show();
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
        Name = findViewById(R.id.edt_ten);
        Pass2 = findViewById(R.id.edt_pass2);
        Pass1 = findViewById(R.id.edt_pass1);
        Sodt = findViewById(R.id.edt_sdt);
        DongY = findViewById(R.id.Btn_dangki);
        Huy = findViewById(R.id.Btn_huy);
        Hoten = findViewById(R.id.edt_hoten);
    }
    private void DangKi(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("success")){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(DangKiActivity.this,DangNhapActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DangKiActivity.this);
                    builder.setMessage("Thêm không thành công ! Trùng tài khoản");
                    builder.setTitle("Thông báo");
                    builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR : "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",Name.getText().toString().trim());
                map.put("userpass",Pass1.getText().toString().trim());
                map.put("Hoten",Hoten.getText().toString().trim());
                map.put("userphone",Sodt.getText().toString().trim());
                map.put("admin",0+"");

                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}

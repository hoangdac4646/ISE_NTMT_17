

        package com.example.black.savemymoneyv3.mActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

        public class Feedback_user extends AppCompatActivity {
    private Toolbar mtoolbar;
    private Button Gui;
    private EditText NoiDung;
    private String UserName;
    private RatingBar ratingBar;
    private int Sao=5;
    private String url="http://ludicrous-disaster.hostingerapp.com/push_feedback.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_user);
       Init();
       ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Sao = (int) rating;
           }
       });
       Gui.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Push_feedback(url);
           }
       });
    }
    public void Push_feedback(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("success")){
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("username",UserName);
                map.put("rating",Sao+"");
                map.put("BinhLuan",NoiDung.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Init(){
        Intent intent = getIntent();
         TaiKhoan a= (com.example.black.savemymoneyv3.DangNhap.TaiKhoan) intent.getSerializableExtra("TaiKhoan");
        UserName = a.getName();
        mtoolbar = findViewById(R.id.feddback_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Gui = findViewById(R.id.btn_gui);
        NoiDung = findViewById(R.id.feedback_noidung);
        ratingBar = findViewById(R.id.ratingBar2);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

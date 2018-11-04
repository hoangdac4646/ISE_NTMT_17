package com.example.black.savemymoneyv3.mActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.DangNhapActivity;
import com.example.black.savemymoneyv3.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBorrowActivity extends AppCompatActivity {
    String url = "http://ludicrous-disaster.hostingerapp.com/Put%20Data/insertDataBorrow.php";
    EditText edtName, edtDate, edtMoney;
    RadioButton rbnChovay, rbnMuontien;
    Button btn_yes;
    private ProgressBar AB_progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrow);
        InitWork();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbnChovay.isChecked() == false && rbnMuontien.isChecked() == false){
                    Toast.makeText(AddBorrowActivity.this, "Vui Lòng Chọn Loại Giao Dịch", Toast.LENGTH_SHORT).show();
                }else if(edtDate.getText().length() == 0 && edtMoney.getText().length() == 0 && edtName.getText().length() == 0){
                    Toast.makeText(AddBorrowActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }
                else if(Long.parseLong(String.valueOf(edtMoney.getText())) < 0){
                    Toast.makeText(AddBorrowActivity.this, "Vui Lòng Nhập Tiền Là Số Dương", Toast.LENGTH_SHORT).show();
                }else {
                    PutData(url);
                }

            }
        });
    }
    private void InitWork(){
        edtName = findViewById(R.id.edtName_AB);
        edtDate = findViewById(R.id.edtDate_AB);
        edtMoney = findViewById(R.id.edtMoney_AB);

        rbnChovay = findViewById(R.id.rbn_chovay);
        rbnMuontien = findViewById(R.id.rbn_muontien);

        btn_yes = findViewById(R.id.btn_yes_AB);
        AB_progressbar = findViewById(R.id.AB_progressbar);
        AB_progressbar.setVisibility(View.INVISIBLE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        edtDate.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

    }

    private void PutData(String url){
        AB_progressbar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddBorrowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddBorrowActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                AB_progressbar.setVisibility(View.INVISIBLE);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                if(rbnMuontien.isChecked()){
                    map.put("loai", 0 +"");// o là mượn
                }
                else {
                    map.put("loai", 1 +"");// 1 là vay
                }
                map.put("tien", edtMoney.getText().toString());
                map.put("tendoituong", edtName.getText().toString());
                map.put("ngay", edtDate.getText().toString());
                map.put("taikhoan", DangNhapActivity.user.getName());
                return map;
            }
        };
        requestQueue.add(request);
    }

}

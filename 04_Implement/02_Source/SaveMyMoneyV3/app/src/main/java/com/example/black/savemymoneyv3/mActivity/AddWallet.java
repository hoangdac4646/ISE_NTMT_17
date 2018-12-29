package com.example.black.savemymoneyv3.mActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.DangNhapActivity;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.ListIconAdapter;
import com.example.black.savemymoneyv3.mFragment.Home_Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddWallet extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtMoney;
    private Button btnXacnhan, btnHuy;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView icon;
    private int pos = 0;
    private final String url = "http://ludicrous-disaster.hostingerapp.com/Put%20Data/insertDataWallet.php";
    private TypedArray imgs;
    private String username;
    private ProgressBar progressBar2;
    private TextView edtDateInit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        InitWork();
        edtDateInit.setText(format.format(Calendar.getInstance().getTime()));

        btnXacnhan.setOnClickListener(this);
        btnHuy.setOnClickListener(this);
        icon.setOnClickListener(this);

    }

    private void InitWork(){
        edtName     = findViewById(R.id.edt_name);
        edtMoney    = findViewById(R.id.edt_money);
        edtDateInit = findViewById(R.id.edt_dateinit);

        btnXacnhan  = findViewById(R.id.btn_xacnhan);
        btnHuy      = findViewById(R.id.btn_huy);
        icon        = findViewById(R.id.imageView);

        imgs        = getResources().obtainTypedArray(R.array.micon);
        username    = DangNhapActivity.user.getName();
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);

        edtDateInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddWallet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar show = Calendar.getInstance();
                        show.set(year,month,dayOfMonth);
                        edtDateInit.setText(format.format(show.getTime()));
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

    }

    private void PutData(String url){
        progressBar2.setVisibility(View.VISIBLE);
        RequestQueue request = Volley.newRequestQueue(AddWallet.this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddWallet.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddWallet.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar2.setVisibility(View.INVISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("tenvi",edtName.getText().toString());
                map.put("tien", edtMoney.getText().toString());
                map.put("ngaytao", edtDateInit.getText().toString());
                map.put("icon", pos+"");
                map.put("taikhoan", username);
                return map;
            }
        };
        request.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_huy:
                finish();
                break;
            case R.id.btn_xacnhan:
                try {
                    format.parse(edtDateInit.getText().toString());
                    if(edtName.getText().length() == 0 || edtMoney.getText().length() == 0 ){
                        Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                    }
                    else if(Long.parseLong(String.valueOf(edtMoney.getText())) < 0){
                        Toast.makeText(AddWallet.this, "Vui Lòng Nhập Tiền Là Số Dương", Toast.LENGTH_SHORT).show();
                    }else {
                        PutData(url);
                        finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Sai Dịnh Dạng Ngày Tháng (2018-01-31)", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.imageView:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.cus_icon_dialog);
                GridView gridView = dialog.findViewById(R.id.list_icon);
                ListIconAdapter adapter = new ListIconAdapter(this, R.layout.cus_grid_icon);
                gridView.setAdapter(adapter);

                dialog.show();
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pos = position;
                        icon.setImageResource(imgs.getResourceId(position, -1));
                        dialog.dismiss();
                    }
                });
        }
    }
}

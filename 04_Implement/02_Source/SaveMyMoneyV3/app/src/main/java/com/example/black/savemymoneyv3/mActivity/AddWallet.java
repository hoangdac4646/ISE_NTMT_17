package com.example.black.savemymoneyv3.mActivity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.example.black.savemymoneyv3.mAdapter.ListIconAdapter;
import com.example.black.savemymoneyv3.mFragment.Home_Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddWallet extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtMoney, edtDateInit;
    Button btnXacnhan, btnHuy;
    SimpleDateFormat format;
    ImageView icon;
    int pos = 0;
    final String url = "https://ludicrous-disaster.000webhostapp.com/Put%20Data/insertDataWallet.php";
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
        edtName = findViewById(R.id.edt_name);
        edtMoney = findViewById(R.id.edt_money);
        edtDateInit= findViewById(R.id.edt_dateinit);

        btnXacnhan = findViewById(R.id.btn_xacnhan);
        btnHuy = findViewById(R.id.btn_huy);
        icon = findViewById(R.id.imageView);

        format = new SimpleDateFormat("yyyy-MM-dd");

    }

    private void PutData(String url){
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
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("tenvi",edtName.getText().toString());
                map.put("tien", edtMoney.getText().toString());
                map.put("ngaytao", edtDateInit.getText().toString());
                map.put("icon", pos+"");
                map.put("taikhoan", "vietdanh");

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
                PutData(url);
                startActivity(new Intent(AddWallet.this, MainActivity.class));
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
                        icon.setImageResource(MainActivity.icon[position]);
                        dialog.dismiss();
                    }
                });



        }
    }
}

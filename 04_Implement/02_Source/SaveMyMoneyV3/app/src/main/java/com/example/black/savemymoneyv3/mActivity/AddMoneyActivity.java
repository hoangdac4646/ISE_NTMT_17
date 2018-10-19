package com.example.black.savemymoneyv3.mActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTienNap, edt_ngayNap, edt_ghichu_AW;
    Button btn_XacNhan;
    Toolbar mtoolbar;
    ImageView img_icon;

    TextView ten, tien, ngaytao;

    final String url = "https://ludicrous-disaster.000webhostapp.com/Put%20Data/insertDataAction.php";

    KhoangChiTieu wallet;
    int loaiGD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        InitWork();
        btn_XacNhan.setOnClickListener(this);
    }

    private void InitWork(){
        edtTienNap      = (EditText) findViewById(R.id.edt_TienNap);
        edt_ngayNap     = (EditText) findViewById(R.id.edt_ngayNap);
        edt_ghichu_AW   = (EditText) findViewById(R.id.edt_ghichu_AW);
        btn_XacNhan     = (Button) findViewById(R.id.btn_xacnhan_AW);
        img_icon        = (ImageView) findViewById(R.id.img_icon_walleadd);

        ten             = (TextView) findViewById(R.id.txtName_AW);
        tien            = (TextView) findViewById(R.id.txtMoney_AW);
        ngaytao         = (TextView) findViewById(R.id.txtDate_AW);
        mtoolbar        = (Toolbar) findViewById(R.id.mtoolbar_AW);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        wallet = (KhoangChiTieu) intent.getSerializableExtra("wallet");
        loaiGD = intent.getIntExtra("loaigd", -1);
        Toast.makeText(this, loaiGD+"", Toast.LENGTH_SHORT).show();
        ten.setText(wallet.getName());
        tien.append(wallet.getMoney() + "");

        img_icon.setImageResource(wallet.getIcon());
        Calendar calendar = wallet.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ngaytao.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_xacnhan_AW){
            PutData(url);
        }
    }

    private void PutData(String url){
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddMoneyActivity.this, response, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoneyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();


                map.put("loaigd", loaiGD +"");
                map.put("tiengd", edtTienNap.getText().toString());
                map.put("ngaygd", edt_ngayNap.getText().toString());
                map.put("ghichu", edt_ghichu_AW.getText().toString());
                map.put("mavi", wallet.getId()+"");


                return map;
            }
        };
        requestQueue.add(request);
    }
}

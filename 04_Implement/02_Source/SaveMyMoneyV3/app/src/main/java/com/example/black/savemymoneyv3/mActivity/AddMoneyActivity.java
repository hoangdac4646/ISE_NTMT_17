package com.example.black.savemymoneyv3.mActivity;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.nfc.FormatException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTienNap, edt_ghichu_AW;
    private Button btn_XacNhan;
    private Toolbar mtoolbar;
    private ImageView img_icon;
    private ProgressBar AM_progressbar;

    private TextView ten, tien, ngaytao, edt_ngayNap;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String url = "http://ludicrous-disaster.hostingerapp.com/Put%20Data/insertDataAction.php";
    private final String urlUpdate = "http://ludicrous-disaster.hostingerapp.com/Update%20Data/updateDataWallet.php";


    KhoangChiTieu wallet;
    int loaiGD;
    TypedArray typedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        InitWork();
        btn_XacNhan.setOnClickListener(this);
    }

    private void InitWork(){
        edtTienNap      = (EditText) findViewById(R.id.edt_TienNap);
        edt_ngayNap     = (TextView) findViewById(R.id.edt_ngayNap);
        edt_ghichu_AW   = (EditText) findViewById(R.id.edt_ghichu_AW);
        btn_XacNhan     = (Button) findViewById(R.id.btn_xacnhan_AW);
        img_icon        = (ImageView) findViewById(R.id.img_icon_walleadd);
        AM_progressbar  = (ProgressBar) findViewById(R.id.AM_progressbar);
        AM_progressbar.setVisibility(View.INVISIBLE);

        ten             = (TextView) findViewById(R.id.txtName_AW);
        tien            = (TextView) findViewById(R.id.txtMoney_AW);
        ngaytao         = (TextView) findViewById(R.id.txtDate_AW);
        mtoolbar        = (Toolbar) findViewById(R.id.mtoolbar_AW);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typedArray = getResources().obtainTypedArray(R.array.micon);
        Intent intent = getIntent();
        wallet = (KhoangChiTieu) intent.getSerializableExtra("wallet");
        loaiGD = intent.getIntExtra("loaigd", -1);
        ten.setText(wallet.getName());
        tien.append(wallet.getMoney() + "");

        img_icon.setImageResource(typedArray.getResourceId(wallet.getIcon(), -1));
        Calendar calendar = wallet.getDate();

        ngaytao.setText(simpleDateFormat.format(calendar.getTime()));
        edt_ngayNap.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        edt_ngayNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMoneyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar show = Calendar.getInstance();
                        show.set(year,month,dayOfMonth);
                        edt_ngayNap.setText(simpleDateFormat.format(show.getTime()));
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
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
            try {
                Calendar testDate = Calendar.getInstance();
                testDate.setTime(simpleDateFormat.parse(edt_ngayNap.getText().toString()));
                simpleDateFormat.format(testDate.getTime());


                if(edt_ghichu_AW.getText().length() == 0 || edtTienNap.getText().length() == 0 || edt_ngayNap.getText().length()== 0){
                    Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin!!", Toast.LENGTH_SHORT).show();

                }
                else if(Long.parseLong(String.valueOf(edtTienNap.getText())) < 0){
                    Toast.makeText(AddMoneyActivity.this, "Vui Lòng Nhập Tiền Là Số Dương", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdateDate(urlUpdate);
                    PutData(url);
                }
            } catch (ParseException e) {
                Toast.makeText(this, "Vui Lòng Nhập Dinh dạng yyyy-MM-dd (2018-01-1)", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this, "Lỗi Định Dạng Ngày tháng năm", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void PutData(String url){
        AM_progressbar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoneyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                AM_progressbar.setVisibility(View.INVISIBLE);
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

    private void UpdateDate(String urlupdate){
        AM_progressbar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, urlupdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddMoneyActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        AM_progressbar.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoneyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                AM_progressbar.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("mavi", wallet.getId()+"");
                map.put("tenvi", wallet.getName());
                if(loaiGD == 0){
                    Long Tien = wallet.getMoney() - Long.parseLong(String.valueOf(edtTienNap.getText()));
                    map.put("tien", Tien+"");

                }
                else{
                    Long Tien = wallet.getMoney() + Long.parseLong(String.valueOf(edtTienNap.getText()));
                    map.put("tien", Tien+"");
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = wallet.getDate();
                map.put("ngaytao", dateFormat.format(calendar.getTime()));
                map.put("icon", wallet.getIcon()+"");
                map.put("taikhoan", wallet.getTaikhoang());

                return map;
            }
        };
        requestQueue.add(request);
    }
}

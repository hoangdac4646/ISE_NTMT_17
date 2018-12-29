package com.example.black.savemymoneyv3.mActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.DuDinh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtKinhPhi, edtghichu;
    private Button yes;
    private Toolbar mtoolbar;
    private String ngaykt;
    private TextView edtNgaydd;
    private ProgressBar AP_progressbar;
    private final String url = "http://ludicrous-disaster.hostingerapp.com/Put%20Data/insertDataPlan.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        initWorks();

        yes.setOnClickListener(this);
    }

    private void initWorks(){
        edtKinhPhi = (EditText) findViewById(R.id.edt_kinhphi);
        edtNgaydd = (TextView) findViewById(R.id.edt_ngaydd);
        edtghichu = (EditText) findViewById(R.id.edt_ghichu_DD);
        yes = (Button) findViewById(R.id.btn_yes_dd);
        mtoolbar = findViewById(R.id.toolbar_AP);
        AP_progressbar = findViewById(R.id.AP_progressbar);
        AP_progressbar.setVisibility(View.INVISIBLE);

        setSupportActionBar(mtoolbar);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        edtNgaydd.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        edtNgaydd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar show = Calendar.getInstance();
                        show.set(year,month,dayOfMonth);
                        edtNgaydd.setText(simpleDateFormat.format(show.getTime()));
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void PutData(String url){
        AP_progressbar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(AddPlanActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddPlanActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                AP_progressbar.setVisibility(View.INVISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("kinhphi", edtKinhPhi.getText().toString());
                map.put("ngaykt", ngaykt);
                map.put("ghichu", edtghichu.getText().toString());
                map.put("taikhoan", DangNhapActivity.user.getName());

                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_yes_dd){

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat1.parse(edtNgaydd.getText().toString()));
                ngaykt = dateFormat1.format(calendar.getTime());
                if(edtKinhPhi.getText().length() == 0 || edtghichu.getText().length() == 0){
                    Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }
                else if(Long.parseLong(String.valueOf(edtKinhPhi.getText())) < 0){
                    Toast.makeText(AddPlanActivity.this, "Vui Lòng Nhập Tiền Là Số Dương", Toast.LENGTH_SHORT).show();
                }else {
                    PutData(url);
                    setResult(RESULT_OK);
                    finish();
                }
            } catch (ParseException e) {
                Toast.makeText(this, "Vui Lòng Nhập Đúng Định Dạng yyyy-MM-dd", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

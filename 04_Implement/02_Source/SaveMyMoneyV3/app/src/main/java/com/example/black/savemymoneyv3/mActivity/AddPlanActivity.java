package com.example.black.savemymoneyv3.mActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.DuDinh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtKinhPhi, edtNgaydd, edtghichu;
    private Button yes;
    private DuDinh duDinh;
    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        initWorks();

        yes.setOnClickListener(this);
    }

    private void initWorks(){
        edtKinhPhi = (EditText) findViewById(R.id.edt_kinhphi);
        edtNgaydd = (EditText) findViewById(R.id.edt_ngaydd);
        edtghichu = (EditText) findViewById(R.id.edt_ghichu_DD);
        yes = (Button) findViewById(R.id.btn_yes_dd);
        mtoolbar = findViewById(R.id.toolbar_AP);

        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            if(v.getId() == R.id.btn_yes_dd){

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateFormat.parse(edtNgaydd.getText().toString()));
                    duDinh = new DuDinh(1,
                            Long.parseLong(String.valueOf(edtKinhPhi.getText())),
                            calendar,
                            edtghichu.getText().toString(), "danh");

                    Intent intent = new Intent();
                    intent.putExtra("data", duDinh);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (ParseException e) {
                    Toast.makeText(this, "Vui Lòng Nhập Đúng Định Dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                }


            }
        }
}

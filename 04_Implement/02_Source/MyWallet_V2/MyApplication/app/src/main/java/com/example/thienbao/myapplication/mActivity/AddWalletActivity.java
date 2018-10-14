package com.example.thienbao.myapplication.mActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thienbao.myapplication.DangNhap.TaiKhoan;
import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mClass.mSQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddWalletActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText wallet_name, wallet_money;
    private TextView label;
    private Button btn_yes, btn_no;
    public mSQLite database;
    ArrayList<String> category;
    Spinner spinner;
    ArrayAdapter adapter;
    TaiKhoan mAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        AnhXa();
        ExcItem();

        spinner.setAdapter(adapter);

    }

    private void AnhXa(){
        label           = findViewById(R.id.title_top);
        wallet_name     = findViewById(R.id.edt_wallet_name_init);
        wallet_money    = findViewById(R.id.edt_wallet_money_init);
        btn_yes         = findViewById(R.id.menu_yes);
        btn_no          = findViewById(R.id.menu_no);
        spinner         = findViewById(R.id.sp_chooseCategory);
        category = new ArrayList<>();
        Collections.addAll(category, getResources().getStringArray(R.array.category_wallet));
        label.setText("Add Wallet");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        database = new mSQLite(this);
        Intent intent = getIntent();
        mAccount = (TaiKhoan) intent.getSerializableExtra("TaiKhoang");
    }

    private void ExcItem(){
        btn_yes.setClickable(false);

        wallet_name.addTextChangedListener(this);

        wallet_money.addTextChangedListener( this);

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(wallet_name.getText().length() != 0 && wallet_money.getText().length() != 0){
            btn_yes.setClickable(true);
            btn_yes.setTextColor(Color.parseColor("#35e738"));
        }
        else {
            btn_yes.setClickable(false);
            btn_yes.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_no:
                finish();
                break;
            case R.id.menu_yes:

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                String temp = simpleDateFormat.format(Calendar.getInstance().getTime());
                String a = (String) spinner.getSelectedItem();
                int category = 0;
                if(a.equals("Ví Tiết Kiệm"))
                {
                    category = 1;
                }
                database.QueryData("INSERT INTO Wallet VALUES(null, '"+
                        wallet_name.getText()+"', "+
                        category+", "+
                        Long.parseLong(String.valueOf(wallet_money.getText()))+", '"+
                        temp+"')");
                Intent intent = new Intent();
                intent.putExtra("Sended", "OK");
                setResult(RESULT_OK, intent);
                finish();
                break;

        }

    }
    private void PutData(String url){
        RequestQueue request = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("","");

                return super.getParams();
            }
        };

        request.add(jsonObjectRequest);
    }

}

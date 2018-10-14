package com.example.thienbao.myapplication.mActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thienbao.myapplication.DangNhap.TaiKhoan;
import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mAdapter.ListWalletAdapter;
import com.example.thienbao.myapplication.mClass.Wallet;
import com.example.thienbao.myapplication.mClass.mSQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WalletInfoActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btn_add_wallet;
    ListView list_choose_wallet;
    ListWalletAdapter adapter;
    ArrayList<Wallet> walletArrayList;
    int Request_code = 100;
    mSQLite database = new mSQLite(this);
    private Button btn_yes, btn_no;
    private TextView label;
    TaiKhoan mAccount;
    private final String url = "http://192.168.1.5/androidwebservice/wallet/getData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);
        AnhXa();
        getData(url);
        ExcItem();

        list_choose_wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WalletInfoActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ExcItem(){
        btn_add_wallet.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    private void AnhXa(){
        label               = findViewById(R.id.title_top);
        btn_add_wallet      = findViewById(R.id.btn_add_wallet);
        list_choose_wallet  = findViewById(R.id.list_choose_wallet);
        btn_yes             = findViewById(R.id.menu_yes);
        btn_no              = findViewById(R.id.menu_no);
        walletArrayList     = new ArrayList<>();

        label.setText("Wallet Information");

        adapter = new ListWalletAdapter(this, R.layout.cus_list_wallet, walletArrayList);
        list_choose_wallet.setAdapter(adapter);

        //Intent intent = getIntent();
        //mAccount = (TaiKhoan) intent.getSerializableExtra("TaiKhoang");
        getData(url);

    }
    private void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(WalletInfoActivity.this);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        walletArrayList.clear();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(simpleDateFormat.parse(object.getString("Ngaytao")));
                                walletArrayList.add(new Wallet(object.getInt("Id_user"),
                                                                object.getInt("Id"),
                                                                object.getString("Name"),
                                                                1,
                                                                object.getLong("Money"),
                                                                 calendar));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WalletInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }


    private void getDatabase(){
        Cursor cursor = database.GetData("SELECT * FROM Wallet");
        walletArrayList.clear();

        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Name = cursor.getString(1);
            int Category = cursor.getInt(2);
            Long money = cursor.getLong(3);
            String initTime = cursor.getString(4);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

            try {
                Date date = simpleDateFormat.parse(initTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                walletArrayList.add(new Wallet(0,ID, Name, Category, money, calendar));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Request_code && resultCode == Activity.RESULT_OK && data != null){
            if(data.getStringExtra("Sended").equals("OK")){
                getDatabase();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add_wallet:
                Intent intent = new Intent(WalletInfoActivity.this, AddWalletActivity.class);
                intent.putExtra("TaiKhoang", mAccount);
                startActivityForResult(new Intent(WalletInfoActivity.this, AddWalletActivity.class), Request_code);
                break;
            case R.id.menu_yes:
                finish();
            case R.id.menu_no:
                finish();;
        }
    }

}

package com.example.black.savemymoneyv3.mActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.ListGDAdapter;
import com.example.black.savemymoneyv3.mClass.GiaoDich;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WalletInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name, money, date;
    private Toolbar mtoolbar;
    private ListView list_WF;
    private ImageView imageView;
    private ArrayList<GiaoDich> items;
    private Button btn_Nap, btn_GD;
    private final int RES_CODE = 2;
    ListGDAdapter adapter;
    KhoangChiTieu wallet;
    private  ProgressDialog mprogress;
    TypedArray imgs;
    Handler handler = new Handler();
    final String url = "https://ludicrous-disaster.000webhostapp.com/Get%20Data/getDataAction.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);

        initwork();


        Intent intent = getIntent();
        wallet = (KhoangChiTieu) intent.getSerializableExtra("wallet");

        name.setText(wallet.getName());
        money.append(wallet.getMoney() + "");
        imageView.setImageResource(imgs.getResourceId(wallet.getIcon(), -1));
        Calendar calendar = wallet.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(simpleDateFormat.format(calendar.getTime()));

        mprogress = ProgressDialog.show(this, "Getting Data From Server", "Loading...", true);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetData(url);

            }
        }, 0);


        btn_Nap.setOnClickListener(this);
        btn_GD.setOnClickListener(this);



    }

    private void initwork(){
        name = findViewById(R.id.txtName_WF);
        money = findViewById(R.id.txtMoney_WF);
        date = findViewById(R.id.txtDate_WF);
        mtoolbar = findViewById(R.id.toolbar_WF);
        list_WF = findViewById(R.id.list_action);
        btn_Nap = findViewById(R.id.btn_Nap);
        btn_GD = findViewById(R.id.btn_GD);
        imageView = (ImageView) findViewById(R.id.img_icon_walletinfo);
        imgs = getResources().obtainTypedArray(R.array.micon);

        items = new ArrayList<>();




        adapter = new ListGDAdapter(this, R.layout.cus_list_action, items);
        list_WF.setAdapter(adapter);



        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }

    private void GetData(String url){
        mprogress.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items.clear();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateFormat.parse(object.getString("ngaygd")));

                                if(wallet.getId() == object.getInt("mavi")) {
                                    items.add(new GiaoDich(object.getInt("magd"),
                                            object.getInt("mavi"),
                                            object.getInt("loaigd"),
                                            object.getString("ghichu"),
                                            calendar, object.getLong("tiengd")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        mprogress.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalletInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RES_CODE && resultCode == Activity.RESULT_OK){

        }
    }

    @Override
    public void onClick(View v) {
        int loaigd = -1;
        Intent intent = new Intent(WalletInfoActivity.this, AddMoneyActivity.class);
        if(v.getId() == R.id.btn_Nap){
            loaigd = 1;
            intent.putExtra("wallet", wallet);
            intent.putExtra("loaigd", loaigd);
            startActivityForResult(intent, RES_CODE);
        }
        if(v.getId() == R.id.btn_GD){
            loaigd = 0;
            intent.putExtra("wallet", wallet);
            intent.putExtra("loaigd", loaigd);
            startActivityForResult(intent, RES_CODE);
        }
    }
}

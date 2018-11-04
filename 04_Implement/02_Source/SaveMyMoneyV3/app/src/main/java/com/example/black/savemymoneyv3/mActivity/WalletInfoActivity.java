package com.example.black.savemymoneyv3.mActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.Map;

public class WalletInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name, money, date;
    private Toolbar mtoolbar;
    private ListView list_WF;
    private ImageView imageView;
    private ArrayList<GiaoDich> items;
    private Button btn_Nap, btn_GD;
    private final int RES_CODE = 2;
    private ListGDAdapter adapter;
    private KhoangChiTieu wallet;
    private  ProgressDialog mprogress;
    private TypedArray imgs;
    private Handler handler = new Handler();
    private final String url = "https://ludicrous-disaster.000webhostapp.com/Get%20Data/getDataAction.php";
    private final String urldelete = "https://ludicrous-disaster.000webhostapp.com/Delete%20Data/deleteDataAction.php";
    private ProgressBar walletProgress;

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
        GetData(url);

        btn_Nap.setOnClickListener(this);
        btn_GD.setOnClickListener(this);
        list_WF.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(WalletInfoActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xoá khoảng chi tiêu này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete(urldelete, position);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return true;
            }
        });



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

        walletProgress = findViewById(R.id.walletProgress);


        adapter = new ListGDAdapter(this, R.layout.cus_list_action, items);
        list_WF.setAdapter(adapter);



        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onResume() {
        super.onResume();
        GetData(url);
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
        walletProgress.setVisibility(View.VISIBLE);
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
                        walletProgress.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalletInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        walletProgress.setVisibility(View.GONE);
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void Delete(final String urldele, final int pos){
        walletProgress.setVisibility(View.VISIBLE);
        RequestQueue requestQueue   = Volley.newRequestQueue(WalletInfoActivity.this);
        StringRequest request       = new StringRequest(Request.Method.POST, urldele,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(WalletInfoActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        GetData(url);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WalletInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                walletProgress.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("magd", items.get(pos).getId_gd() + "");
                return map;
            }
        };
        requestQueue.add(request);


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

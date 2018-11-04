package com.example.black.savemymoneyv3.mActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.DangNhapActivity;
import com.example.black.savemymoneyv3.DangNhap.TaiKhoan;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.ListBorrowAdapter;
import com.example.black.savemymoneyv3.mClass.Borrows;
import com.example.black.savemymoneyv3.mServer.BorrowService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowActivity extends AppCompatActivity {
    private ListView listView;
    private ListBorrowAdapter adapter;
    private Toolbar mtoolbar;
    private ArrayList <Borrows> items;
    private Button btn_them;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ProgressBar progressBar3;
    private final String url = "http://ludicrous-disaster.hostingerapp.com/Get%20Data/getDataBorrow.php";
    private final String urldelete = "http://ludicrous-disaster.hostingerapp.com/Delete%20Data/deleteDataBorrow.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        InitWork();
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BorrowActivity.this, AddBorrowActivity.class));
            }//on click
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(BorrowActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xoá không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete(urldelete, position);
                        getData(url);
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

    private void InitWork(){
        mtoolbar = findViewById(R.id.mtoolbar_BA);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_them = findViewById(R.id.btn_them_BA);
        listView = findViewById(R.id.list_borrow);
        items = new ArrayList<>();
        adapter = new ListBorrowAdapter(this, R.layout.cus_list_borrow, items );
        listView.setAdapter(adapter);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.INVISIBLE);

        getData(url);

    }//init

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(url);
    }

    private void getData(String url){
        progressBar3.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items.clear();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);

                                if(DangNhapActivity.user.getName().equals(object.getString("taikhoan"))){
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(simpleDateFormat.parse(object.getString("ngay")));
                                    items.add(new Borrows(object.getInt("mavm"),
                                            object.getLong("tien"),
                                            object.getInt("loai"),
                                            object.getString("tendoituong"),
                                            calendar,
                                            object.getString("taikhoan")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }//catch date
                        }//for
                        adapter.notifyDataSetChanged();
                        progressBar3.setVisibility(View.INVISIBLE);
                    }//respone
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BorrowActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void Delete(final String urldele, final int pos){
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest request       = new StringRequest(Request.Method.POST, urldele,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BorrowActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        getData(url);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BorrowActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("mavm", items.get(pos).getId() + "");
                return map;
            }
        };
        requestQueue.add(request);


    }





}

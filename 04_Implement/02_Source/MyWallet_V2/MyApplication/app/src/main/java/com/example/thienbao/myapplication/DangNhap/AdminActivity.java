package com.example.thienbao.myapplication.DangNhap;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thienbao.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    final String url_read ="http://192.168.1.4:1080/Webserve/getData.php";
    private ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = findViewById(R.id.rcv_result);
        layoutManager = new LinearLayoutManager(AdminActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter =new Adapter_Admin(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ReadTaiKhoan(url_read);
        //Nagative button menu
        navigationView = findViewById(R.id.navigation_header_container);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.draw_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nv_open,R.string.nv_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setCheckedItem(R.id.ad_danhmuc);
        navigationView.setCheckedItem(R.id.ad_deal);
        navigationView.setCheckedItem(R.id.ad_user);
        navigationView.setCheckedItem(R.id.ad_phanhoi);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void ReadTaiKhoan(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        list.add(new TaiKhoan(object.getString("Name"),object.getString("MatKhau"),
                                object.getString("SoDt"),object.getString("ViTri"),object.getString("Hoten")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.ad_danhmuc) {
            ReadTaiKhoan(url_read);
        }
        else  if(item.getItemId()==R.id.ad_phanhoi) {
            Toast.makeText(getApplicationContext(),"Phan hoi",Toast.LENGTH_SHORT).show();
        }
        else  if(item.getItemId()==R.id.ad_user) {
            Toast.makeText(getApplicationContext(),"QL user",Toast.LENGTH_SHORT).show();
        }
        else  if(item.getItemId()==R.id.ad_deal) {
            Toast.makeText(getApplicationContext(),"Deal",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

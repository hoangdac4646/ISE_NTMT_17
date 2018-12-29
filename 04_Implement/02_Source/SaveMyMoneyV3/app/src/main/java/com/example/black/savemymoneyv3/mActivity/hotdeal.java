package com.example.black.savemymoneyv3.mActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.adapter_anhdeal;
import com.example.black.savemymoneyv3.mClass.deal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class hotdeal  extends AppCompatActivity {
    Toolbar mtoolbar;
    GridView gridView;
    List<deal> Arr_deal = new ArrayList<>();
    adapter_anhdeal anhdeal;
    final String url_read ="http://ludicrous-disaster.hostingerapp.com/get_hotdeal.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotdeal_activity);
        mtoolbar = findViewById(R.id.toolbar_1);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridView = findViewById(R.id.grid_hotdeal);
        GetDeal(url_read);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Arr_deal.get(position).getUrl_Link()));
                startActivity(browserIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void GetDeal(String url){
        Arr_deal = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(hotdeal.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id = Integer.parseInt(object.getString("Id"));
                        String Anh = object.getString("Anh");
                        String Link = object.getString("Link");
                        Arr_deal.add(new deal(id,Anh,Link));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                anhdeal = new adapter_anhdeal(hotdeal.this,R.layout.custom_anhdeal,Arr_deal);
                gridView.setAdapter(anhdeal);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(hotdeal.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}


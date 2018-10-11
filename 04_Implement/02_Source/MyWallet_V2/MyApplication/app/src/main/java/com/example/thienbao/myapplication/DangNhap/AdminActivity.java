package com.example.thienbao.myapplication.DangNhap;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.thienbao.myapplication.mFragment.QlFeedback_fragment;
import com.example.thienbao.myapplication.mFragment.QlTaiKhoan_fragment;
import com.example.thienbao.myapplication.mFragment.QlTaiKhoan_hotdeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final String url_read ="https://ludicrous-disaster.000webhostapp.com/getData.php";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        QlTaiKhoan_fragment qlTaiKhoan_fragment = new QlTaiKhoan_fragment();
        android.app.FragmentManager fragmentManager  = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
        fragmentTransaction.commit();
        //Nagative button menu
        navigationView = findViewById(R.id.navigation_header_container);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.draw_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nv_open,R.string.nv_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        if(getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.ad_danhmuc) {
          //  ReadTaiKhoan(url_read);


        }
        else  if(item.getItemId()==R.id.ad_phanhoi) {
            QlFeedback_fragment qlTaiKhoan_fragment = new QlFeedback_fragment();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
            return true;
        }
        else  if(item.getItemId()==R.id.ad_user) {
            QlTaiKhoan_fragment qlTaiKhoan_fragment = new QlTaiKhoan_fragment();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
            return true;
        }
        else  if(item.getItemId()==R.id.ad_deal) {
            QlTaiKhoan_hotdeal qlTaiKhoan_fragment = new QlTaiKhoan_hotdeal();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}

package com.example.black.savemymoneyv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.mActivity.AboutUsActivity;
import com.example.black.savemymoneyv3.mActivity.AddWallet;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;
import com.example.black.savemymoneyv3.mFragment.Home_Fragment;
import com.example.black.savemymoneyv3.mFragment.Plan_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentTransaction mTransaction;
    private Fragment mFragment;
    private BottomNavigationView bottomNavigationView;
    public static final int[] icon = {R.drawable.pic0, R.drawable.pic0, R.drawable.pic0, R.drawable.pic0, R.drawable.pic0,
            R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,
            R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,
            R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,
            R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,R.drawable.pic0,};
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWork();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void initWork(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
         bottomNavigationView = findViewById(R.id.bottom_navigation);
         mTransaction = getSupportFragmentManager().beginTransaction();
         mFragment = new Home_Fragment();
         mTransaction.replace(R.id.home_linear, mFragment);
         mTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = null;
        switch (item.getItemId()){
            case R.id.nav_home:
                mFragment = new Home_Fragment();
                mTransaction.replace(R.id.home_linear, mFragment);
                break;
            case R.id.nav_add:
               startActivity(new Intent(MainActivity.this, AddWallet.class));
                break;
            case R.id.nav_plan:
                mFragment = new Plan_Fragment();
                mTransaction.replace(R.id.home_linear, mFragment);
                break;
            case R.id.nav_aboutus:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                return true;
            case R.id.nav_quit:
                finish();
                System.exit(0);
                return true;
        }
        mTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

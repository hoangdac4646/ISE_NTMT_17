package com.example.thienbao.myapplication.mActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thienbao.myapplication.DangNhap.TaiKhoan;
import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mFragment.Home_Fragment;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    Fragment mFragment;
    FragmentTransaction transaction;
    TaiKhoan loginAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Init();


        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void Init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        mtoggle = new ActionBarDrawerToggle(
                Main2Activity.this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        transaction = getSupportFragmentManager().beginTransaction();
        mFragment = new Home_Fragment();

        transaction.add(R.id.main_Fragment, mFragment);
        transaction.commit();
        Intent intent   = getIntent();
        loginAccount = (TaiKhoan) intent.getSerializableExtra("TaiKhoan");

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((Home_Fragment) mFragment).GetDatabase();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(mtoggle.onOptionsItemSelected(item)){
           return true;
       }
       if(item.getItemId() == android.R.id.home){
           return true;
       }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId())
        {
            case R.id.nav_account:

                break;
            case R.id.nav_vallet_info:
                Intent intent = new Intent(Main2Activity.this, WalletInfoActivity.class);
                //intent.putExtra("TaiKhoang", loginAccount);
                startActivity(intent);
                break;
            case R.id.nav_about:
                startActivity(new Intent(Main2Activity.this, AboutUsActivity.class));
                break;
            case R.id.nav_add:
                startActivity(new Intent(Main2Activity.this, AddTransactionActivity.class));
                break;
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


package com.example.black.savemymoneyv3.DangNhap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mFragment.QlFeedback_fragment;
import com.example.black.savemymoneyv3.mFragment.QlTaiKhoan_fragment;
import com.example.black.savemymoneyv3.mFragment.QlTaiKhoan_hotdeal;


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

          if(item.getItemId()==R.id.ad_phanhoi) {
            QlFeedback_fragment qlTaiKhoan_fragment = new QlFeedback_fragment();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
              drawerLayout.closeDrawers();
            return true;
        }
        else  if(item.getItemId()==R.id.ad_user) {
            QlTaiKhoan_fragment qlTaiKhoan_fragment = new QlTaiKhoan_fragment();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
              drawerLayout.closeDrawers();
            return true;
        }
        else  if(item.getItemId()==R.id.ad_deal) {
            QlTaiKhoan_hotdeal qlTaiKhoan_fragment = new QlTaiKhoan_hotdeal();
            android.app.FragmentManager fragmentManager  = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.Frg_layoutAdmin,qlTaiKhoan_fragment);
            fragmentTransaction.commit();
              drawerLayout.closeDrawers();
            return true;
        }
        else if(item.getItemId()==R.id.ad_logout){
              drawerLayout.closeDrawers();
            finish();
        }
        drawerLayout.closeDrawers();
        return false;
    }
}

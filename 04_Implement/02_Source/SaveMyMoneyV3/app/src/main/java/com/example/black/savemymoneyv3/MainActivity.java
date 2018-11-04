package com.example.black.savemymoneyv3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
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
import com.example.black.savemymoneyv3.mActivity.AboutUsActivity;
import com.example.black.savemymoneyv3.mActivity.AddWallet;
import com.example.black.savemymoneyv3.mActivity.Edit_thongtincanhan;
import com.example.black.savemymoneyv3.mActivity.Feedback_user;
import com.example.black.savemymoneyv3.mActivity.NgoaiTe_Activity;
import com.example.black.savemymoneyv3.mActivity.Xem_thongtincanhan;
import com.example.black.savemymoneyv3.mClass.CanChuyen;
import com.example.black.savemymoneyv3.mClass.Communicator;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;
import com.example.black.savemymoneyv3.mFragment.Home_Fragment;
import com.example.black.savemymoneyv3.mFragment.Plan_Fragment;
import com.example.black.savemymoneyv3.mFragment.ThongKe_fragment;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener, Communicator {

    private List<String> tenvi = new ArrayList<>();
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentTransaction mTransaction;
    private ArrayList<com.example.black.savemymoneyv3.mClass.CanChuyen> Data_Chuyen= new ArrayList<>();
    private Fragment mFragment;
    private BottomNavigationView bottomNavigationView;
    TaiKhoan loginAccount;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWork();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Intent intent2   = getIntent();
        loginAccount = (TaiKhoan) intent2.getSerializableExtra("TaiKhoan");

       ReadTk("http://ludicrous-disaster.hostingerapp.com/tien.php");

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

        Intent intent = getIntent();
        TaiKhoan user = (TaiKhoan) intent.getSerializableExtra("TaiKhoan");
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
            case R.id.nav_report:
                  mFragment = new ThongKe_fragment();
                Bundle args = new Bundle();
                args.putSerializable("CanChuyen",Data_Chuyen);
               mFragment.setArguments(args);
               mTransaction.replace(R.id.home_linear, mFragment, "tag");

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
            case R.id.nav_logout:
                startActivity(new Intent(MainActivity.this, DangNhapActivity.class));
                break;
            case R.id.nav_thongtincanhan:
                Intent intentGui2 = new Intent(MainActivity.this, Xem_thongtincanhan.class);
                intentGui2.putExtra("TaiKhoan",loginAccount);
                startActivity(intentGui2);
                break;
            case R.id.nav_thaydoithongtin:
               Intent intentGui = new Intent(MainActivity.this, Edit_thongtincanhan.class);
               intentGui.putExtra("TaiKhoan",loginAccount);
                startActivity(intentGui);
                break;
            case R.id.nav_more:
               View view = findViewById(R.id.nav_more);

                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_more,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.menu_convert){
                            startActivity(new Intent(MainActivity.this, NgoaiTe_Activity.class));
                        }
                        return false;
                    }
                });
                popupMenu.show();

                break;
            case R.id.nav_feelback:
                Intent intent_feedback = new Intent(MainActivity.this, Feedback_user.class);
                intent_feedback.putExtra("TaiKhoan",loginAccount);
                startActivity(intent_feedback);
                break;
            case R.id.nav_add:
                Intent intent = new Intent(MainActivity.this, AddWallet.class);
                startActivity(intent);
                break;

        }
        mTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void ReadTk(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Data_Chuyen.add(new CanChuyen(jsonObject.getString("TenVi"),jsonObject.getString("MaVi")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Lổi ! Kiểm tra lại ",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("username",loginAccount.getName().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void Communicate(String key ,String data) {

    }
}

package com.example.black.savemymoneyv3.DangNhap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.black.savemymoneyv3.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;

public class StartActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private slidelayout adapter;
    private TextView[] mDots;
    private Button DangKi,DangNhap;
    public static SharedPreferences mPrefs;
    private static  final String    PRESS_NAME  ="prefsFile";
    String name="";
    String pass="";
    public Boolean getPreferences(){
        SharedPreferences sp  = getSharedPreferences(PRESS_NAME,MODE_PRIVATE);
            try{
                if(sp.contains("pref_name")){
                     name  = sp.getString("pref_name","not");
                     if(name.length()==0){
                         return  false;
                     }
                }
                if(sp.contains("pref_pass")) {
                     pass = sp.getString("pref_pass", "not found");
                    if(pass.length()==0){
                        return  false;
                    }
                    return true;
                }
            }catch(Exception e){
                return false;
            }
            return false;
    }
    final String url = "http://ludicrous-disaster.hostingerapp.com/Login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        LiferayScreensContext.init(this);
        mPrefs = getSharedPreferences(PRESS_NAME,MODE_PRIVATE);
        Boolean Check = getPreferences();
        if(Check==true){
            DangNhapActivity.ReadTaiKhoan(url,StartActivity.this,name,pass);
        }
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout  =findViewById(R.id.dotsLayout);
        DangNhap = findViewById(R.id.Btn_dn);
        DangKi = findViewById(R.id.Btn_dk);
        adapter = new slidelayout(this);
        mSlideViewPager.setAdapter(adapter);
        addDotSIndicator(0);
        mSlideViewPager.addOnPageChangeListener(listener);
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dang nhap
                startActivity(new Intent(StartActivity.this,DangNhapActivity.class));

            }
        });
        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dang ki tai khoan

                startActivity(new Intent(StartActivity.this,DangKiActivity.class));

            }
        });
    }
    public void addDotSIndicator(int position){
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTrans));
            mDotLayout.addView(mDots[i]);

        }
        if(mDots.length >0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotSIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

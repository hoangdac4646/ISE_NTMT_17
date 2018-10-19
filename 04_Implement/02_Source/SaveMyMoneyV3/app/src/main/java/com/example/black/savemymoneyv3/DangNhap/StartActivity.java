package com.example.black.savemymoneyv3.DangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.DangNhap.StartActivity;
import com.example.black.savemymoneyv3.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;

public class StartActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private slidelayout adapter;
    private TextView[] mDots;
    private Button DangKi,DangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        LiferayScreensContext.init(this);
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

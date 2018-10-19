package com.example.black.savemymoneyv3.DangNhap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.black.savemymoneyv3.R;


public class slidelayout extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public slidelayout(Context context){
        this.context = context;
    }
    public int[] slide_Image = {R.drawable.heo, R.drawable.can,R.drawable.bieudo,R.drawable.thongke};
    public String[] slide_decr = {"Mẹo đầu tiên: Đăng nhập để truy cập vào dữ liệu của bạn trên thiết bị",
            "Quản lí chi tiêu cá nhân và hoàn thành mục tiêu tài chính","Kiểm soát thu chi hiệu quả với hệ thống biểu đồ ",
    "Lên kế hoạch tài chính thông minh và từng bước tiết kiệm để hiện thực hóa giấc mơ"};

    @Override
    public int getCount() {
        return slide_Image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.slide,container,false);
        ImageView imageView = v.findViewById(R.id.img_slide);
        TextView textViewDes = v.findViewById(R.id.Tv_slide);
        imageView.setImageResource(slide_Image[position]);
        textViewDes.setText(slide_decr[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}


package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class HoatDongAdapter extends BaseAdapter {
    private Context context;
    private int Layout;
    private TypedArray imgs;
    private ArrayList<KhoangChiTieu> items;



    public HoatDongAdapter(Context context, int layout, ArrayList<KhoangChiTieu> items) {
        this.context = context;
        Layout = layout;
        this.items = items;
        imgs = context.getResources().obtainTypedArray(R.array.micon);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        ImageView imgIcon_CT;
        TextView name, money;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(Layout, parent, false);

            holder.imgIcon_CT = view.findViewById(R.id.imgIcon_CT);
            holder.name = view.findViewById(R.id.txtName_CT);
            holder.money = view.findViewById(R.id.txtMoney_CT);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.imgIcon_CT.setImageResource(imgs.getResourceId(items.get(position).getIcon(), -1));
        holder.name.setText(items.get(position).getName());
        holder.money.setText(items.get(position).getMoney() + "");

        return view;
    }
}

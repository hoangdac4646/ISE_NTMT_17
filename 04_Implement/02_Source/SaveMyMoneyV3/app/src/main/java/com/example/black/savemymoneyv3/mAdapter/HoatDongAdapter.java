package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
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

public class HoatDongAdapter extends BaseAdapter {
    private Context context;
    private int Layout;
    private ArrayList<KhoangChiTieu> items;

    public HoatDongAdapter(Context context, int layout, ArrayList<KhoangChiTieu> items) {
        this.context = context;
        Layout = layout;
        this.items = items;
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
        ImageView icon;
        TextView name, money;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(Layout, parent, false);

            holder.icon = view.findViewById(R.id.imgIcon);
            holder.name = view.findViewById(R.id.txtName);
            holder.money = view.findViewById(R.id.txtMoney);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.icon.setBackgroundResource(items.get(position).getIcon());
        holder.icon.setImageResource(items.get(position).getIcon());
        holder.name.setText(items.get(position).getName());
        holder.money.setText(items.get(position).getMoney() + "");

        return view;
    }
}

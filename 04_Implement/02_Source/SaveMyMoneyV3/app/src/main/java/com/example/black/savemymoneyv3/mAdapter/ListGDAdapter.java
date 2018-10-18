package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.GiaoDich;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListGDAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<GiaoDich> items;

    public ListGDAdapter(Context context, int layout, ArrayList<GiaoDich> items) {
        this.context = context;
        this.layout = layout;
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
        TextView money, date , ghichu;
        ImageView imgIcon;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            holder.money    = view.findViewById(R.id.txt_money_L);
            holder.date     = view.findViewById(R.id.txt_date_L);
            holder.ghichu   = view.findViewById(R.id.txt_ghichu_L);
            holder.imgIcon  = view.findViewById(R.id.ic_changes);

            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        int loaigd = items.get(position).getLoaigd();

        if(loaigd == 0){
            holder.money.setTextColor(Color.parseColor("#ff0000"));
            holder.date.setTextColor(Color.parseColor("#ff0000"));
            holder.ghichu.setTextColor(Color.parseColor("#ff0000"));
            holder.imgIcon.setImageResource(R.drawable.ic_sub);

        }
        else
        {
            holder.imgIcon.setImageResource(R.drawable.ic_add);

            holder.money.setTextColor(Color.parseColor("#35e738"));
            holder.date.setTextColor(Color.parseColor("#35e738"));
            holder.ghichu.setTextColor(Color.parseColor("#35e738"));
        }

        holder.money.setText(items.get(position).getMoney() + "");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


        holder.date.setText(format.format(items.get(position).getCalendar().getTime()));

        holder.ghichu.setText(items.get(position).getGhichu());


        return view;
    }
}

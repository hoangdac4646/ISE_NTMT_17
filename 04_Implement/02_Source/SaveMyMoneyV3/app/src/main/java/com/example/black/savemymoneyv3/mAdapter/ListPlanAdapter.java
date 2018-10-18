package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.DuDinh;
import com.example.black.savemymoneyv3.mClass.GiaoDich;

import java.util.ArrayList;

public class ListPlanAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<DuDinh> items;

    public ListPlanAdapter(Context context, int layout, ArrayList<DuDinh> items) {
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
    private class ViewHolder{
        TextView txt_kinhphi ;
        TextView txt_ghichu;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            holder.txt_ghichu = view.findViewById(R.id.txt_ghichu_DD);
            holder.txt_kinhphi = view.findViewById(R.id.txt_kinhphi);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_kinhphi.setText(items.get(position).getKinhPhi() + "");
        holder.txt_ghichu.setText(items.get(position).getGhiChu());

        return view;
    }

}

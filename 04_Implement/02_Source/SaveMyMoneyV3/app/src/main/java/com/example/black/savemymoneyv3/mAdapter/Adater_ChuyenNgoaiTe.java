package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.NgoaiTe;

import java.util.ArrayList;

public class Adater_ChuyenNgoaiTe extends ArrayAdapter<NgoaiTe> {
     Context context;
    int resource;
    ArrayList<NgoaiTe> objects;
    public Adater_ChuyenNgoaiTe(@NonNull Context context, int resource, @NonNull ArrayList<NgoaiTe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }
    public class ViewHolder {
        private ImageView Hinh;
        private TextView Ten,MoTa;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return Init(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return Init(position,convertView,parent);
    }
    public View Init(int position,View convertView,ViewGroup parent){
        ViewHolder holder = new ViewHolder();
        View view  =convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_ngoaite,parent,false);
            holder.Hinh = view.findViewById(R.id.img_NgoaiTeQG);
            holder.Ten  =view.findViewById(R.id.tv_NgoaiTeTenQg);
            holder.MoTa  =view.findViewById(R.id.tv_NgoaiTemieuta);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.Hinh.setImageResource(objects.get(position).getHinh());
        holder.Ten.setText(objects.get(position).getTen());
        holder.MoTa.setText(objects.get(position).getMieuTa());
        return view;

    }
}

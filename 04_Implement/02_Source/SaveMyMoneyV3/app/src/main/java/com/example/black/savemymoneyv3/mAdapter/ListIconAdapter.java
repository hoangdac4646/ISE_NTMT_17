package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListIconAdapter extends BaseAdapter {
    Context context;
    int layout;

    TypedArray imgs;

    public ListIconAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;

         imgs = context.getResources().obtainTypedArray(R.array.micon);

    }

    @Override
    public int getCount() {
        return imgs.length();
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
        ImageView icons_img;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.icons_img = view.findViewById(R.id.icons);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.icons_img.setImageResource(imgs.getResourceId(position, -1));

        return view;
    }
}

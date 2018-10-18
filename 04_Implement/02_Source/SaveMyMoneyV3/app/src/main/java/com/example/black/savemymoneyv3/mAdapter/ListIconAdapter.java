package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;

public class ListIconAdapter extends BaseAdapter {
    Context context;
    int layout;
    int[] icons = MainActivity.icon;

    public ListIconAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return icons.length;
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
            view = inflater.inflate(layout, parent, false);

            holder.icons_img = view.findViewById(R.id.icons);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.icons_img.setImageResource(icons[position]);

        return view;
    }
}

package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.feddback_ad;

import java.util.List;

public class Admin_feedback extends ArrayAdapter<feddback_ad> {
    private Context context;
    private int resource;
    private List<feddback_ad> objects;
    public Admin_feedback(@NonNull Context context, int resource, @NonNull List<feddback_ad> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects=objects;
        this.resource =resource;
    }

    public class ViewHolder {
        private TextView user,mes;
        private RatingBar ratingBar;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View view = convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,parent,false);
            holder.user = view.findViewById(R.id.feedback_tk);
            holder.mes = view.findViewById(R.id.feedback_mes);
            holder.ratingBar = view.findViewById(R.id.ratingBar);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.user.setText(objects.get(position).getUser());
        holder.mes.setText(objects.get(position).getMes());
        holder.ratingBar.setRating(objects.get(position).getSao());
        Drawable progress = holder.ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.RED);
        return view;
    }
}

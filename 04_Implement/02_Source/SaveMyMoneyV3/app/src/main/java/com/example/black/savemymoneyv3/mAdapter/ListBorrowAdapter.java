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
import com.example.black.savemymoneyv3.mClass.Borrows;
import com.example.black.savemymoneyv3.mClass.Communicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListBorrowAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Borrows> items;

    public ListBorrowAdapter(Context context, int layout, ArrayList<Borrows> items) {
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
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView icon;
        TextView txtName, txtMoney, txtDate;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
       ViewHolder holder;
       if(view == null){
           holder = new ViewHolder();
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(layout,parent , false);
           holder.icon      = view.findViewById(R.id.img_Icon_BA);
           holder.txtName   = (TextView) view.findViewById(R.id.txtName_BA);
           holder.txtDate   = (TextView) view.findViewById(R.id.txtDate_BA);
           holder.txtMoney  = (TextView) view.findViewById(R.id.txtMoney_BA);

           view.setTag(holder);

       }else{
           holder = (ViewHolder) view.getTag();
       }
        if(items.get(position).getLoai() == 0){
            holder.icon.setImageResource(R.drawable.brorrow);
            holder.txtMoney.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
           holder.icon.setImageResource(R.drawable.lent);
            holder.txtMoney.setTextColor(Color.parseColor("#35e738"));

        }
        holder.txtName.setText(items.get(position).getName());
        holder.txtMoney.setText(items.get(position).getMoney() +"");
        Calendar calendar = items.get(position).getNgay();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.txtDate.setText(simpleDateFormat.format(calendar.getTime()));
        return view;
    }
}

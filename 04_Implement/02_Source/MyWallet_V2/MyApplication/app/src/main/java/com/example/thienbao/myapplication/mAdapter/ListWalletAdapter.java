package com.example.thienbao.myapplication.mAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mClass.Wallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListWalletAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Wallet> items;

    public ListWalletAdapter(Context context, int layout, ArrayList<Wallet> items) {
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
    public class ViewHolder{
        TextView txt_money_used, txt_caption, txt_decription;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            holder.txt_caption      = view.findViewById(R.id.txt_caption);
            holder.txt_decription   = view.findViewById(R.id.txt_decription);
            holder.txt_money_used   = view.findViewById(R.id.txt_money_used);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }


        holder.txt_caption.setText(items.get(position).getWallet_name());
        holder.txt_money_used.setText(String.valueOf(items.get(position).getMoney()));

        Calendar calendar = items.get(position).getInit_time();
        calendar.get(Calendar.DATE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.txt_decription.setText(simpleDateFormat.format(calendar.getTime()));






        return view;
    }
}

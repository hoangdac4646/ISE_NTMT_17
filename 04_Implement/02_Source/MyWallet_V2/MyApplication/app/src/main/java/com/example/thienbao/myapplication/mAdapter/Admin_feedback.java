package com.example.thienbao.myapplication.mAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.thienbao.myapplication.mClass.feddback_ad;
import com.example.thienbao.myapplication.R;

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
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.user.setText(objects.get(position).getUser());
        holder.mes.setText(objects.get(position).getMes());
        return view;
    }
}

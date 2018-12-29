package com.example.black.savemymoneyv3.mAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.deal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_anhdeal  extends ArrayAdapter<deal> {
    Context context;
    int resource;
    List<deal> objects;
    public adapter_anhdeal( Context context, int resource, List<deal> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource =resource;
        this.objects = objects;
    }

   class ViewHolder{
        ImageView imageView;
   }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view
                 = convertView;
        ViewHolder holder = new ViewHolder();
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(resource,parent,false);
            holder.imageView = view.findViewById(R.id.img_hotdeal);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        try{
            Picasso.get().load(objects.get(position).getUrl_anh()).into(holder.imageView);

        }catch(Exception e){

        }
        return view;
    }
}

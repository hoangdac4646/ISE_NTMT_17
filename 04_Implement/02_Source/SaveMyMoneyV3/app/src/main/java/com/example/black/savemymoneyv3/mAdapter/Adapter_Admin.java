package com.example.black.savemymoneyv3.mAdapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.TaiKhoan;
import com.example.black.savemymoneyv3.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_Admin extends RecyclerView.Adapter<Adapter_Admin.ViewHoder> {
    private ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
    private Context context;

    public Adapter_Admin(ArrayList<TaiKhoan> list, Context context) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_viewadmin, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, final int position) {
        holder.TaiKhoan.setText(list.get(position).getName().toString());
        holder.MatKhau.setText(list.get(position).getPass().toString());
        holder.HoTen.setText(list.get(position).getHoTen().toString());
        holder.SoDt.setText(list.get(position).getSodt().toString());
        if (list.get(position).getViTri().toString().equals("1")) {
            holder.ViTri.setText("Quản trị");
            holder.ViTri.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else if(list.get(position).getViTri().toString().equals("0")) {
            holder.ViTri.setText("Khách hàng");
            holder.ViTri.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        }
        holder.ChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_admindialog);
                dialog.setCanceledOnTouchOutside(true);
                //anh xa
                TextView ten = dialog.findViewById(R.id.tv_tentk);
                final TextView tenuser = dialog.findViewById(R.id.edt_ChinhSuaTen);
                final RadioGroup group = dialog.findViewById(R.id.Rdg_quantri);
                if (holder.ViTri.getText().toString().equals("Khách hàng")) {
                    RadioButton button = dialog.findViewById(R.id.rdb_khachHang);
                    button.setChecked(true);
                } else if (holder.ViTri.getText().toString().equals("Quản trị")) {
                    RadioButton button = dialog.findViewById(R.id.rdb_quanli);
                    button.setChecked(true);
                }
               ten.setText(holder.TaiKhoan.getText());
                tenuser.setText(holder.HoTen.getText().toString());
                ten.setTextSize(15);
                Button Xoa, Sua, Huy;
                Xoa = dialog.findViewById(R.id.btnXoaTk);
                Sua = dialog.findViewById(R.id.btn_ChinhSualuu);
                Huy = dialog.findViewById(R.id.btn_ChinhHuy);
                Sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tenuser.getText().length() ==0){
                            Toast.makeText(context,"Lổi trống họ tên",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String url = "http://ludicrous-disaster.hostingerapp.com/Update.php";
                            RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // =1 chuyen ve tai khoan admin
                                    if (response.equals("1")) {
                                        list.get(position).setViTri("1");
                                        list.get(position).setHoTen(tenuser.getText().toString());
                                        notifyItemChanged(position);
                                        dialog.dismiss();
                                    }
                                    // Chuyen ve tai khoan khac hang
                                    else if (response.equals("0")) {
                                        list.get(position).setViTri("0");
                                        list.get(position).setHoTen(tenuser.getText().toString());
                                        notifyItemChanged(position);
                                        dialog.dismiss();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("username", holder.TaiKhoan.getText().toString().trim());
                                    map.put("userpass", holder.MatKhau.getText().toString().trim());
                                    if (group.getCheckedRadioButtonId() == dialog.findViewById(R.id.rdb_quanli).getId()) {
                                        map.put("ViTri", 1 + "");
                                    } else if (group.getCheckedRadioButtonId() == dialog.findViewById(R.id.rdb_khachHang).getId()) {
                                        map.put("ViTri", 0 + "");
                                    }
                                    map.put("HoTen", tenuser.getText().toString().trim());
                                    return map;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }
                });


                Huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "http://ludicrous-disaster.hostingerapp.com/Delete.php";
                        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("Success")) {
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("username", holder.TaiKhoan.getText().toString().trim());
                                map.put("userpass", holder.MatKhau.getText().toString().trim());
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });

                dialog.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder{
        private TextView TaiKhoan,MatKhau,SoDt,ViTri;
        private Button ChinhSua;
        private TextView HoTen;
        public ViewHoder(View itemView) {
            super(itemView);
           TaiKhoan = itemView.findViewById(R.id.tv_adtk);
           MatKhau = itemView.findViewById(R.id.tv_admk);
            SoDt = itemView.findViewById(R.id.tv_adnumber);
            ViTri = itemView.findViewById(R.id.tv_advitri);
            ChinhSua  = itemView.findViewById(R.id.btn_adChinhSua);
            HoTen =itemView.findViewById(R.id.tv_adTenUser);
        }
    }

}

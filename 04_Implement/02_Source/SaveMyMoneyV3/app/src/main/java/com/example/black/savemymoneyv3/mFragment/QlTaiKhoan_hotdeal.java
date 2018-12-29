package com.example.black.savemymoneyv3.mFragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.adapter_anhdeal;
import com.example.black.savemymoneyv3.mClass.deal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QlTaiKhoan_hotdeal extends Fragment {
    GridView listView;
      adapter_anhdeal anhdeal;
      List<deal> Arr_deal = new ArrayList<>();
    final String url_read ="http://ludicrous-disaster.hostingerapp.com/get_hotdeal.php";
    Button Them;
    String url="http://ludicrous-disaster.hostingerapp.com/push_deal.php";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlhotdeal,container,false);
        listView = view.findViewById(R.id.lv_hotdeal);
        Them = view.findViewById(R.id.btn_themhotdeal);
        anhdeal = new adapter_anhdeal(getActivity(),R.layout.custom_anhdeal,Arr_deal);
        listView.setAdapter(anhdeal);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int ViTri=position;
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có muốn xóa hot deal này không ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, "http://ludicrous-disaster.hostingerapp.com/delete_deal.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                        if(response.indexOf("Success")!=-1){
                                            Arr_deal.remove(ViTri);
                                            anhdeal = new adapter_anhdeal(getActivity(),R.layout.custom_anhdeal,Arr_deal);
                                            listView.setAdapter(anhdeal);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(),"ERROR : "+error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> map = new HashMap<>();
                                        map.put("id",Arr_deal.get(ViTri).getId()+"");
                                        return map;
                                    }
                                };
                                requestQueue.add(jsonObjectRequest);
                            }
                        });
                builder.create().show();
                }
                });

                Them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_dealdialog);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                        final EditText Anh, Link;
                        Button DongY;
                        Link = dialog.findViewById(R.id.edit_themlink);
                        Anh = dialog.findViewById(R.id.edit_themanh);
                        DongY = dialog.findViewById(R.id.btn_dongyThem);
                        DongY.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Link.getText().length() != 0 && Anh.getText().length() != 0) {

                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                    StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("success")) {
                                                Link.setText("");
                                                Anh.setText("");
                                                GetDeal(url_read);
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "ERROR : " + error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> map = new HashMap<>();
                                            map.put("anh", Anh.getText().toString().trim());
                                            map.put("link", Link.getText().toString().trim());
                                            return map;
                                        }
                                    };
                                    requestQueue.add(jsonObjectRequest);
                                }
                            }
                        });
                    }
                });

        GetDeal(url_read);
        return view;
    }

    void GetDeal(String url){
        Arr_deal = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                       int id = Integer.parseInt(object.getString("Id"));
                       String Anh = object.getString("Anh");
                       String Link = object.getString("Link");
                       Arr_deal.add(new deal(id,Anh,Link));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                anhdeal = new adapter_anhdeal(getActivity(),R.layout.custom_anhdeal,Arr_deal);
                listView.setAdapter(anhdeal);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}

package com.example.black.savemymoneyv3.mFragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.DangNhap.DangNhapActivity;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mActivity.AddBorrowActivity;
import com.example.black.savemymoneyv3.mActivity.BorrowActivity;
import com.example.black.savemymoneyv3.mActivity.WalletInfoActivity;
import com.example.black.savemymoneyv3.mAdapter.HoatDongAdapter;
import com.example.black.savemymoneyv3.mClass.Communicator;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Home_Fragment extends Fragment implements Communicator {

    private TextView sum_money;
    private ListView list_ChiTieu;
    private ArrayList<KhoangChiTieu> items;
    private HoatDongAdapter adapter;
    private Context context;
    private MainActivity mainActivity;
    private ProgressDialog progress;
    private ImageView img_reset;
    private String username ;
    private LinearLayout borrow_linear;
    private TextView sum_borrow, sum_lent;
    private ProgressBar homeprogress;
    private int MAvi = 0;

    private final String url = "http://ludicrous-disaster.hostingerapp.com/Get%20Data/getDataWallet.php";
    private final String url1 = "http://ludicrous-disaster.hostingerapp.com/Get%20Data/getDataBorrow.php";
    private final String urldelete = "http://ludicrous-disaster.hostingerapp.com/Delete%20Data/deleteDataWallet.php";

    TypedArray imgs;
    boolean isrunning = false;

    public Home_Fragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mainActivity = (MainActivity) getActivity();
        imgs = context.getResources().obtainTypedArray(R.array.micon);
        username = DangNhapActivity.user.getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        list_ChiTieu    = view.findViewById(R.id.list_chitieu);
        sum_money       = view.findViewById(R.id.txt_sum_money_main);
        img_reset       = view.findViewById(R.id.img_reset);
        borrow_linear   = view.findViewById(R.id.borrow_linear);
        sum_borrow      = view.findViewById(R.id.txtSumBorrow);
        sum_lent      = view.findViewById(R.id.txtSumLent);
        homeprogress    = view.findViewById(R.id.homeprogress);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        items = new ArrayList<>();
        adapter = new HoatDongAdapter(context, R.layout.cus_list_chitieu, items);
        list_ChiTieu.setAdapter(adapter);
        GetData(url);
        GetBorrowData(url1);



        list_ChiTieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WalletInfoActivity.class);
                intent.putExtra("wallet", items.get(position));
                startActivity(intent);
            }
        });

        list_ChiTieu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xoá khoảng chi tiêu này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete(urldelete, position);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return true;
            }
        });
        img_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isrunning = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        if(isrunning)
                        {
                            img_reset.animate().rotationBy(360).withEndAction(this).setDuration(1000).setInterpolator(new LinearInterpolator()).start();
                            GetData(url);
                        }
                        isrunning = false;
                    }
                }, 0);
            }
        });
        borrow_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BorrowActivity.class));
            }
        });


    }




    @Override
    public void onResume() {
        super.onResume();
        GetData(url);

        GetBorrowData(url1);

    }

    private void GetData(final String url) {
        homeprogress.setVisibility(View.VISIBLE);
        RequestQueue request = Volley.newRequestQueue(mainActivity);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items.clear();
                        Long sum = 0L;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();

                                JSONObject object = response.getJSONObject(i);
                                if(username.equals(object.getString("taikhoan"))) {
                                    calendar.setTime(simpleDateFormat.parse(object.getString("ngaytao")));
                                    MAvi = object.getInt("mavi");
                                    items.add(new KhoangChiTieu(object.getInt("mavi"),
                                            object.getInt("icon"),
                                            object.getString("tenvi"),
                                            object.getLong("tien"),
                                            calendar,
                                            object.getString("taikhoan")));

                                    sum += object.getLong("tien");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        sum_money.setText(sum + "");
                        adapter.notifyDataSetChanged();
                        homeprogress.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                homeprogress.setVisibility(View.GONE);
            }
        });
        request.add(jsonArrayRequest);
    }
    private void GetBorrowData(final String url) {
        homeprogress.setVisibility(View.VISIBLE);
        RequestQueue request = Volley.newRequestQueue(mainActivity);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Long sumB = 0L;
                        Long SumL = 0L;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getString("taikhoan").equals(DangNhapActivity.user.getName())) {
                                    if (object.getInt("loai") == 0) {
                                        sumB += object.getLong("tien");
                                    } else {
                                        SumL += object.getLong("tien");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sum_borrow.setText(sumB+"");
                        sum_lent.setText(SumL+"");
                        homeprogress.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                homeprogress.setVisibility(View.GONE);

            }
        });
        request.add(jsonArrayRequest);
    }

    private void Delete(final String urldele, final int pos){
        homeprogress.setVisibility(View.VISIBLE);
        RequestQueue requestQueue   = Volley.newRequestQueue(context);
            StringRequest request       = new StringRequest(Request.Method.POST, urldele,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            homeprogress.setVisibility(View.GONE);
                            GetData(url);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    homeprogress.setVisibility(View.GONE);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("mavi", items.get(pos).getId() + "");
                    return map;
                }
            };
            requestQueue.add(request);


    }


    @Override
    public void Communicate(String key, String data) {
        if(key.equals("username")) {
            username = data;
        }
    }
}

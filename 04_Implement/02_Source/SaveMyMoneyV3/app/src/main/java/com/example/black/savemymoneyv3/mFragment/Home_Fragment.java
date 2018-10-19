package com.example.black.savemymoneyv3.mFragment;


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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mActivity.WalletInfoActivity;
import com.example.black.savemymoneyv3.mAdapter.HoatDongAdapter;
import com.example.black.savemymoneyv3.mClass.KhoangChiTieu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Home_Fragment extends Fragment {

    TextView sum_money;
    ListView list_ChiTieu;
    ArrayList<KhoangChiTieu> items;
    HoatDongAdapter adapter;
    Context context;
    MainActivity mainActivity;
    ProgressDialog progress;
    ImageView img_reset;

    final String url = "https://ludicrous-disaster.000webhostapp.com/Get%20Data/getDataWallet.php";
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        list_ChiTieu    = view.findViewById(R.id.list_chitieu);
        sum_money       = view.findViewById(R.id.txt_sum_money_main);
        img_reset       = view.findViewById(R.id.img_reset);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        items = new ArrayList<>();
        adapter = new HoatDongAdapter(context, R.layout.cus_list_chitieu, items);
        list_ChiTieu.setAdapter(adapter);


        progress = ProgressDialog.show(getActivity(), "Getting Data From Server", "Loading...", true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetData(url);
            }
        }, 0);


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
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xoá khoảng chi tiêu này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xoá đi
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


    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetData(url);
            }
        }, 0);
    }

    private void GetData(final String url) {
        progress.show();
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
                                calendar.setTime(simpleDateFormat.parse(object.getString("ngaytao")));

                                items.add(new KhoangChiTieu(object.getInt("mavi"),
                                        object.getInt("icon"),
                                        object.getString("tenvi"),
                                        object.getLong("tien"),
                                        calendar,
                                        object.getString("taikhoan")));

                                sum += object.getLong("tien");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        sum_money.setText(sum + "");
                        adapter.notifyDataSetChanged();
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonArrayRequest);
    }

}

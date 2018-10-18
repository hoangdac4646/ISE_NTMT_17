package com.example.black.savemymoneyv3.mFragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    final String url = "https://ludicrous-disaster.000webhostapp.com/Get%20Data/getDataWallet.php";
    //final static int[] icon = {R.drawable.pic0, R.drawable.pic0};
    int[] icon;
    public Home_Fragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mainActivity = (MainActivity) getActivity();
        GetData(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        list_ChiTieu    = view.findViewById(R.id.list_chitieu);
        sum_money       = view.findViewById(R.id.txt_sum_money_main);
        icon = MainActivity.icon;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        items = new ArrayList<>();
        //icon = getActivity().getResources().getIntArray(R.array.iconARR);
        Calendar calendar = Calendar.getInstance();
        adapter = new HoatDongAdapter(context, R.layout.cus_list_chitieu, items);
        list_ChiTieu.setAdapter(adapter);

        GetData(url);

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


    }

    @Override
    public void onResume() {
        super.onResume();
        GetData(url);
    }
    private void GetData(String url){
        RequestQueue request = Volley.newRequestQueue(mainActivity);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items.clear();
                        Long sum = 0L;
                        for(int i = 0; i < response.length(); i++){
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar calendar = Calendar.getInstance();

                                JSONObject object = response.getJSONObject(i);
                                calendar.setTime(simpleDateFormat.parse(object.getString("ngaytao")));

                                items.add(new KhoangChiTieu(object.getInt("mavi"),
                                        icon[object.getInt("icon")],
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

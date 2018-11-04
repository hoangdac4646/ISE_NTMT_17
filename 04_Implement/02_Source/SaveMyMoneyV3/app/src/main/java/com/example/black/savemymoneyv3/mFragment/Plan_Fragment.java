package com.example.black.savemymoneyv3.mFragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mActivity.AddPlanActivity;
import com.example.black.savemymoneyv3.mAdapter.ListPlanAdapter;
import com.example.black.savemymoneyv3.mClass.Communicator;
import com.example.black.savemymoneyv3.mClass.DuDinh;
import com.example.black.savemymoneyv3.mClass.GiaoDich;
import com.example.black.savemymoneyv3.mServer.BorrowService;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Plan_Fragment extends Fragment implements View.OnClickListener, Communicator {

    CompactCalendarView compactCalendarView;
    Button btn_them;
    TextView txtShow;
    Context context;
    ListView list_plan;
    int RES_CODE = 123;
    private ListPlanAdapter adapter;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private ArrayList<DuDinh> plans;
    private final String url = "http://ludicrous-disaster.hostingerapp.com/Get%20Data/getDataPlan.php";
    private String username;
    private final String urldelete = "http://ludicrous-disaster.hostingerapp.com/Delete%20Data/deleteDataPlan.php";
    private ProgressBar planPregress;
    private ArrayList<String> dates;
    public Plan_Fragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_, container, false);
        initWork(view);
        btn_them.setOnClickListener(this);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        username = DangNhapActivity.user.getName();
        adapter = new ListPlanAdapter(context, R.layout.cus_list_plan, plans);
        list_plan.setAdapter(adapter);


        GetData(url);









        list_plan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xoá khoảng chi tiêu này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete(urldelete, position);
                        GetData(url);
                        plans.remove(position);
                        adapter.notifyDataSetChanged();
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


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                plans.clear();
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                if(events.size() != 0) {
                    for (int i = 0; i < events.size(); i++) {
                        Event temp = events.get(i);
                        DuDinh plan = (DuDinh) temp.getData();
                        plans.add(plan);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtShow.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private void Delete(final String urldele, final int pos){
        planPregress.setVisibility(View.VISIBLE);
        RequestQueue requestQueue   = Volley.newRequestQueue(context);
        StringRequest request       = new StringRequest(Request.Method.POST, urldele,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                        planPregress.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                planPregress.setVisibility(View.GONE);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                if(pos < 0){ // pos < 0 là các dự dinh có ngàykt < ngày hiện tại và pos củng là madd chỉ cần đổi đấu lại
                    map.put("madd", -pos + "");
                }
                else // do người dùng xoá tại 1 vị trí trong list
                {
                    map.put("madd", plans.get(pos).getId_gd() + "");
                }
                return map;
            }
        };
        requestQueue.add(request);


    }

    @Override
    public void onResume() {
        super.onResume();
        GetData(url);
    }

    private void initWork(View view){
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        btn_them = view.findViewById(R.id.btn_them_DD);
        txtShow = view.findViewById(R.id.show_m_y);
        list_plan= view.findViewById(R.id.list_plan);
        compactCalendarView.displayOtherMonthDays(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        planPregress = view.findViewById(R.id.planPregress);
        planPregress.setVisibility(View.GONE);

        txtShow.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        plans = new ArrayList<>();
        dates = new ArrayList<>();



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_them_DD){
            Intent intent = new Intent(context, AddPlanActivity.class);
            startActivityForResult( intent, RES_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RES_CODE && resultCode == Activity.RESULT_OK){
            GetData(url);
        }
    }



    private void GetData(String url){
        planPregress.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        compactCalendarView.removeAllEvents();
                        dates.clear();
                        plans.clear();
                        DuDinh duDinh;
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String today = dateFormat.format(Calendar.getInstance().getTime());
                                Calendar calen = Calendar.getInstance();
                                calen.setTime(dateFormat.parse(today));

                                if(username.equals(object.getString("taikhoan"))) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(dateFormat.parse(object.getString("ngaykt")));

                                    if(calendar.getTimeInMillis() < calen.getTimeInMillis()){
                                        int temp  = -(object.getInt("madd")); //set pos < 0 de biết là nó được xoá ngay từ đầu để goi hàm
                                        Delete(urldelete, temp);
                                    }
                                    else {
                                        duDinh = new DuDinh(object.getInt("madd"),
                                                object.getLong("kinhphi"),
                                                calendar,
                                                object.getString("ghichu"),
                                                object.getString("taikhoan"));
                                        Event mevent = new Event(Color.YELLOW, calendar.getTimeInMillis(), duDinh);
                                        compactCalendarView.addEvent(mevent);
                                        if (today.equals(object.getString("ngaykt"))) {
                                            plans.add(duDinh);
                                        }
                                        dates.add(object.getString("ngaykt"));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Lỗi Dịnh Dạng", Toast.LENGTH_SHORT).show();
                            }// catch2
                            planPregress.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        } //for
                        Intent intent = new Intent(context, BorrowService.class);
                        intent.putStringArrayListExtra("dates", dates);
                        context.startService(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        planPregress.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void Communicate(String key, String data) {
        if(key.equals("username")){
            username = data;
        }
    }
}

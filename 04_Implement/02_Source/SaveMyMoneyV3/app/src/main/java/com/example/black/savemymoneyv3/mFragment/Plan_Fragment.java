package com.example.black.savemymoneyv3.mFragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mActivity.AddPlanActivity;
import com.example.black.savemymoneyv3.mAdapter.ListPlanAdapter;
import com.example.black.savemymoneyv3.mClass.DuDinh;
import com.example.black.savemymoneyv3.mClass.GiaoDich;
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
import java.util.List;
import java.util.Locale;

public class Plan_Fragment extends Fragment implements View.OnClickListener {

    CompactCalendarView compactCalendarView;
    Button btn_them;
    TextView txtShow;
    Context context;
    ListView list_plan;
    int RES_CODE = 123;
    private ListPlanAdapter adapter;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ArrayList<DuDinh> plans;
    final String url = "https://ludicrous-disaster.000webhostapp.com/Get%20Data/getDataPlan.php";
    private ProgressDialog progress;
    private  Handler handler = new Handler();


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

        progress = ProgressDialog.show(getActivity(), "Getting Data From Server", "Loading...", true);

        handler.post(new Runnable() {
            @Override
            public void run() {
                GetData(url);
            }
        });

        adapter = new ListPlanAdapter(context, R.layout.cus_list_plan, plans);
        list_plan.setAdapter(adapter);

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

    private void initWork(View view){
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        btn_them = view.findViewById(R.id.btn_them_DD);
        txtShow = view.findViewById(R.id.show_m_y);
        list_plan= view.findViewById(R.id.list_plan);
        compactCalendarView.displayOtherMonthDays(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        txtShow.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        plans = new ArrayList<>();






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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DuDinh duDinh;
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateFormat.parse(object.getString("ngaykt")));


                                    duDinh = new DuDinh(object.getInt("madd"),
                                            object.getLong("kinhphi"),
                                            calendar,
                                            object.getString("ghichu"),
                                            object.getString("taikhoan"));
                                Event mevent = new Event(Color.YELLOW, calendar.getTimeInMillis(), duDinh);
                                compactCalendarView.addEvent(mevent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            progress.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}

package com.example.black.savemymoneyv3.mFragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.CanChuyen;
import com.example.black.savemymoneyv3.mClass.GiaoD;
import com.example.black.savemymoneyv3.mClass.GiaoDich;
import com.example.black.savemymoneyv3.mClass.ThuChi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKe_fragment extends android.support.v4.app.Fragment {
    private BarChart barChart;
    private int TongThu=0,TongChi=0;
    private TextView TienThu,TienChi;
    String aaaa;
    static ArrayList<BarEntry> barEntries;
    static ArrayList<BarEntry> barEntries2;
    private ArrayList<CanChuyen>  a = new ArrayList<>();
    List<String> MaVi = new ArrayList<>();
    List<String> TenCuaVi = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke,container,false);

        Bundle args = getArguments();
       a = (ArrayList<CanChuyen>) args.getSerializable("CanChuyen");
        TienChi = view.findViewById(R.id.tv_chi);
        TienThu = view.findViewById(R.id.tv_thu);
        barChart = view.findViewById(R.id.barchar);
       for(int i=0;i<a.size();i++) {
            TenCuaVi.add(a.get(i).getTenVi());
            MaVi.add(a.get(i).getMaVi());
        }
        Read("http://ludicrous-disaster.hostingerapp.com/GetThuChi.php",MaVi,TenCuaVi);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       /* Bundle args = getArguments();
        a = (ArrayList<CanChuyen>) args.getSerializable("CanChuyen");
        for(int i=0;i<a.size();i++) {
            TenCuaVi.add(a.get(i).getTenVi());
            MaVi.add(a.get(i).getMaVi());
        }
        Read("http://ludicrous-disaster.hostingerapp.com/GetThuChi.php",MaVi,TenCuaVi);*/
    }

    public void Read(String url, final List<String> a, final List<String> vi2  ){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //List<ThuChi> Thu_Chi = new ArrayList<ThuChi>();
                List<GiaoD> arr = new ArrayList<GiaoD>();
                try {
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int CheckViCoKhong=0;
                        //Kiem tra ma vi trong mảng ví trong tk hiện tại
                        for(int Dem=0;Dem<a.size();Dem++){
                            String Temp = a.get(Dem);
                            if(jsonObject.getString("MaViSd").toString().trim().equals(a.get(Dem))){

                                int KiemTraCoKo=0;
                                int Check = Integer.parseInt(jsonObject.getString("TongTien").toString());
                                //Kiểm tra mã ví đã tồn tại chưa chưa thì thêm mới rồi cộng tiền vào
                                for(int k =0;k<arr.size();k++){
                                    //Neu ton tai trong mang arr --> Chi can cong them tien
                                    if(a.get(Dem).equals(arr.get(k).getMavi())){
                                        if(jsonObject.getString("LoaiGd").toString().equals("0")){
                                            arr.get(k).setTienChi(Check);
                                        }else{
                                            arr.get(k).setTienThu(Check);
                                        }
                                        KiemTraCoKo=1;
                                        break;
                                    }
                                }

                                //Neu ko ton tai them moi
                                if(KiemTraCoKo==0){
                                    if( jsonObject.getString("LoaiGd").toString().equals("0")){

                                        arr.add(new GiaoD(0,Check,jsonObject.getString("MaViSd").toString()));
                                    }else{
                                        arr.add(new GiaoD(Check,0,jsonObject.getString("MaViSd").toString()));
                                    }
                                }
                                break;
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Ve bieu do
                ArrayList<BarEntry> bar_tienthu =  new ArrayList<>();
                int Thu=0,Chi=0;
                for(int i=0;i<arr.size();i++){
                    Thu+= arr.get(i).getTienThu();
                    bar_tienthu.add(new BarEntry(i+1,arr.get(i).getTienThu()));
                }

                ArrayList<BarEntry> bar_tienchi =  new ArrayList<>();
                for(int i=0;i<arr.size();i++){
                    Chi+= arr.get(i).getThienChi();
                    bar_tienchi.add(new BarEntry(i+1,arr.get(i).getThienChi()));
                }
                TienThu.setText(Thu+"");
                TienChi.setText(Chi+"");
                BarDataSet barDataSet = new BarDataSet(bar_tienthu,"Tiền thu");
                barDataSet.setColor(getResources().getColor(R.color.Red));
                BarDataSet barDataSet2 = new BarDataSet(bar_tienchi,"Tiền chi");
                barDataSet2.setColor(getResources().getColor(R.color.Xanh));
                BarData barData = new BarData(barDataSet,barDataSet2);
                barChart.setData(barData);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(vi2));
                xAxis.setCenterAxisLabels(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1);
                xAxis.setGranularityEnabled(true);
                barChart.setDragEnabled(true);
                barChart.animateY(4000);
                barChart.setVisibleXRangeMaximum(3);
                //(0.3
                float groupSpace = 0.15f;
                float barSpace  =0.05f;
                float barWidth = 0.375f;
                barData.setBarWidth(barWidth);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*bar_tienchi.size());
                barChart.groupBars(0,groupSpace,barSpace);
                barChart.getAxisLeft().setAxisMinimum(0);
                barChart.invalidate();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"ERROR  "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Mavi",1+"");
                return map;
            }};
        requestQueue.add(jsonArrayRequest);
    }

}

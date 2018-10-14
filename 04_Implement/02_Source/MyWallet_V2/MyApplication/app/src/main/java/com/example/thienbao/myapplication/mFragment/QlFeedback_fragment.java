package com.example.thienbao.myapplication.mFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thienbao.myapplication.mClass.feddback_ad;
import com.example.thienbao.myapplication.DangNhap.Adapter_Admin;
import com.example.thienbao.myapplication.DangNhap.TaiKhoan;
import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mAdapter.Admin_feedback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QlFeedback_fragment extends Fragment {
    ListView listView;
    Admin_feedback admin_feedback;
    final String url_read ="https://ludicrous-disaster.000webhostapp.com/getFeedBack.php";
    private List<feddback_ad> list = new ArrayList<feddback_ad>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlfeedback,container,false);
        listView  = view.findViewById(R.id.lsv_feedback);
       admin_feedback = new Admin_feedback(getActivity().getApplication(),R.layout.custom_feedback,list);
     listView.setAdapter(admin_feedback);
        ReadTaiKhoan(url_read);
      admin_feedback.notifyDataSetChanged();

        return view;
    }
    private void ReadTaiKhoan(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                       list.add(new feddback_ad(object.getString("Name").toString(),object.getString("ND").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                admin_feedback.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(arrayRequest);
    }
}
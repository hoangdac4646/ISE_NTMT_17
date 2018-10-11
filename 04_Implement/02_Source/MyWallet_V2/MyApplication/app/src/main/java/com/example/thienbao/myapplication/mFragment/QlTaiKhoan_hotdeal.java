package com.example.thienbao.myapplication.mFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thienbao.myapplication.DangNhap.TaiKhoan;
import com.example.thienbao.myapplication.R;

import java.util.ArrayList;

public class QlTaiKhoan_hotdeal extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    final String url_read ="https://ludicrous-disaster.000webhostapp.com/getData.php";
    private ArrayList<TaiKhoan> list = new ArrayList<TaiKhoan>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlhotdeal,container,false);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
      //  recyclerView.setHasFixedSize(true);
      //  recyclerView.setLayoutManager(layoutManager);
      //  list.add(new TaiKhoan("asdas","123","123123","1","Le Van"));
       // list.add(new TaiKhoan("ddd","123","123344123","1","Le Van"));
       // list.add(new TaiKhoan("as2131das","123","12345343123","1","Le Van"));
       // list.add(new TaiKhoan("asasdasddas","123","123123","1","Le Van"));
       // adapter =new Adapter_Admin(list,getActivity());
       // recyclerView.setAdapter(adapter);
       // adapter.notifyDataSetChanged();


        return view;

    }
}

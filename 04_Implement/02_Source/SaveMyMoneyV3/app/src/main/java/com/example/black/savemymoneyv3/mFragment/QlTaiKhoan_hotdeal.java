package com.example.black.savemymoneyv3.mFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.black.savemymoneyv3.DangNhap.TaiKhoan;
import com.example.black.savemymoneyv3.R;

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



        return view;

    }
}

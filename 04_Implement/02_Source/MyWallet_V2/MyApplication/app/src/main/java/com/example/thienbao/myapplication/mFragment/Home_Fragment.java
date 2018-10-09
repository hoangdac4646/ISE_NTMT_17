package com.example.thienbao.myapplication.mFragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mClass.mSQLite;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    TextView sum_money;
    mSQLite database ;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        sum_money = view.findViewById(R.id.txt_sum_money_main);
        database = new mSQLite(getActivity());


        return view;
    }

    private void Init(View view){
        sum_money = view.findViewById(R.id.txt_sum_money_main);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //database = new mSQLite(getActivity());
        GetDatabase();
    }

    public void GetDatabase(){

        mSQLite database = new mSQLite(getActivity());
        Long money = Long.valueOf(0);
        Cursor data = database.GetData("SELECT * FROM Wallet");
        while (data.moveToNext()){
            money +=  data.getLong(3);
        }
        sum_money.setText(money + "");
    }

}
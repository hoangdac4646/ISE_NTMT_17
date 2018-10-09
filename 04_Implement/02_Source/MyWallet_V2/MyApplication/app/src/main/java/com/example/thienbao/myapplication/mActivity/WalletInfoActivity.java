package com.example.thienbao.myapplication.mActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thienbao.myapplication.R;
import com.example.thienbao.myapplication.mAdapter.ListWalletAdapter;
import com.example.thienbao.myapplication.mClass.Wallet;
import com.example.thienbao.myapplication.mClass.mSQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WalletInfoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    LinearLayout btn_add_wallet;
    ListView list_choose_wallet;
    ListWalletAdapter adapter;
    ArrayList<Wallet> walletArrayList;
    int Request_code = 100;
    mSQLite database = new mSQLite(this);
    private Button btn_yes, btn_no;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);
        AnhXa();
        ExcItem();
        getDatabase();
    }

    private void ExcItem(){
        btn_add_wallet.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        list_choose_wallet.setOnItemSelectedListener(this);
    }

    private void AnhXa(){
        label = findViewById(R.id.title_top);
        btn_add_wallet = findViewById(R.id.btn_add_wallet);
        list_choose_wallet  =findViewById(R.id.list_choose_wallet);
        btn_yes         = findViewById(R.id.menu_yes);
        btn_no          = findViewById(R.id.menu_no);
        walletArrayList = new ArrayList<>();

        label.setText("Wallet Information");

        adapter = new ListWalletAdapter(this, R.layout.cus_list_wallet, walletArrayList);
        list_choose_wallet.setAdapter(adapter);

        getDatabase();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getDatabase();
    }

    @Override
    protected void onResume() {

        super.onResume();
        getDatabase();
    }

    private void getDatabase(){
        Cursor cursor = database.GetData("SELECT * FROM Wallet");
        walletArrayList.clear();

        while (cursor.moveToNext()){
            int ID = cursor.getInt(0);
            String Name = cursor.getString(1);
            int Category = cursor.getInt(2);
            Long money = cursor.getLong(3);
            String initTime = cursor.getString(4);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

            try {
                Date date = simpleDateFormat.parse(initTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                walletArrayList.add(new Wallet(ID, Name, Category, money, calendar));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Request_code && resultCode == Activity.RESULT_OK && data != null){
            if(data.getStringExtra("Sended").equals("OK")){
                getDatabase();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add_wallet:
                startActivityForResult(new Intent(WalletInfoActivity.this, AddWalletActivity.class), Request_code);
                break;
            case R.id.menu_yes:
                Intent intent = new Intent();
            case R.id.menu_no:
                finish();;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.example.thienbao.myapplication.mActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.thienbao.myapplication.R;


public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_yes,btn_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Init();

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    private void Init(){
        btn_yes         = findViewById(R.id.menu_yes);
        btn_no          = findViewById(R.id.menu_no);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.menu_yes:
                finish();
                break;
            case R.id.menu_no:
                finish();
                break;
        }
    }
}

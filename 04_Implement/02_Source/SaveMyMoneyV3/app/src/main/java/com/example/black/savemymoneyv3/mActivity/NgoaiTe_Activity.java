package com.example.black.savemymoneyv3.mActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mAdapter.Adater_ChuyenNgoaiTe;
import com.example.black.savemymoneyv3.mClass.NgoaiTe;

import java.util.ArrayList;


public class NgoaiTe_Activity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private ArrayList<NgoaiTe> list, list2;
    private Adater_ChuyenNgoaiTe adaterChuyenNgoaiTe;
    private Spinner spinner_1;
    private Spinner spinner_2;
    private int ViTri1 = 0, ViTri2 = 0;
    private ImageView chuyen2ben, chuyentien;
    private EditText Tien;
    private TextView TienCanDoi,TienSauDoi,nenTien1,nenTien2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoaite);
        Init();
        adaterChuyenNgoaiTe = new Adater_ChuyenNgoaiTe(this, R.layout.custom_ngoaite, list);
        spinner_1.setAdapter(adaterChuyenNgoaiTe);
        spinner_2.setAdapter(adaterChuyenNgoaiTe);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViTri1 = position;
                nenTien1.setText(list.get(position).getTen().toString()+" TO ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nenTien1.setText("");
            }
        });
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViTri2 = position;
                nenTien2.setText(list.get(position).getTen().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nenTien2.setText("");
            }
        });

        chuyen2ben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NgoaiTe a = list.get(ViTri1);
                list.set(ViTri1, list.get(ViTri2));
                list.set(ViTri2, a);
                nenTien1.setText(list.get(ViTri1).getTen().toString()+" TO ");
                nenTien2.setText(list.get(ViTri2).getTen().toString());
                adaterChuyenNgoaiTe.notifyDataSetChanged();
            }
        });
        chuyentien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tien.getText().toString().length() ==0){
                    Toast.makeText(getApplicationContext(),"Không được để trống ! Kiểm tra lại ",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Tien.getText().toString().length() > 40) {
                        Toast.makeText(getApplicationContext(), "Lổi tràn số ! Kiểm tra lại ", Toast.LENGTH_SHORT).show();
                    } else {

                        if (ViTri2 == ViTri1) {
                            TienCanDoi.setText(Tien.getText().toString() + "  " + list.get(ViTri1).getTen() + " = ");
                            TienSauDoi.setText(Tien.getText().toString() + "  " + list.get(ViTri2).getTen() + "");
                        } else {
                            Double DoiSangUS = Double.parseDouble(Tien.getText().toString());
                            double x = DoiSangUS * list.get(ViTri1).getTiGia();
                            x  =x * list.get(ViTri2).getTiGiaDola();
                            if(x >0.001) {
                                String a = ChuyenTien(String.valueOf(x));
                                TienCanDoi.setText(Tien.getText().toString() + "  " + list.get(ViTri1).getTen() + " = ");
                                TienSauDoi.setText(a.toString() + "  " + list.get(ViTri2).getTen() + "");
                            }else{
                                Toast.makeText(getApplicationContext(), "Số tiền quá nhỏ để quy đổi ! Kiểm tra lại ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    public String ChuyenTien(String Temp) {
       //2.3456E13 vi tri = 1 dem=13
        //23456000000000
        int Check=0,Vitri=0;
        String a = Temp;
        StringBuilder DaySo,Dem;
        Dem = new StringBuilder();
        DaySo = new StringBuilder();
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)=='E'){
                Check=1;
            }
            else if(Check==1){
                Dem.append(a.charAt(i));
            }
            else if(a.charAt(i)=='.'){
                Vitri=i;
            }
            else if(Check!=1  ){
                DaySo.append(a.charAt(i));
            }
        }
        if(Dem.length()==0){
            String Result = ThemDayPhay(Temp);
            return Result;
        }
        int SoLan  =Integer.parseInt(Dem.toString());
        SoLan = SoLan - (DaySo.toString().length()-Vitri);
        if(SoLan<=0){
            while (SoLan <0) {
                DaySo.deleteCharAt(DaySo.length()-1);
                SoLan++;
            }
        }
        else {
            while (SoLan > 0) {
                DaySo.append("0");
                SoLan--;
            }
        }

        String Result = ThemDayPhay(DaySo.toString());
        return Result.toString();
    }
    public String ThemDayPhay(String DaySo){
        int CheckPhay=0;
        StringBuilder Dem = new StringBuilder();
        StringBuilder aa  = new StringBuilder();
        if(DaySo.toString().length() >=5) {
            for (int i = DaySo.toString().length() - 1; i >= 0; i--) {
                if (CheckPhay == 3) {
                    Dem.append(',');
                    CheckPhay = 0;
                    i++;
                }else if(DaySo.charAt(i)=='.'){
                    Dem.append(DaySo.charAt(i));
                    CheckPhay=0;
                    StringBuilder NewTemp = new StringBuilder();
                    for(int j=0;j<Dem.length();j++){
                        if(Dem.toString().charAt(j)!=','){
                            NewTemp.append(Dem.toString().charAt(j));
                        }
                    }
                    Dem.setLength(0);
                    Dem.append(NewTemp.toString());
                }

                else {
                    Dem.append(DaySo.charAt(i));
                    CheckPhay++;
                }
            }

            for (int i = Dem.length() - 1; i >= 0; i--) {
                aa.append(Dem.charAt(i));
            }
            return aa.toString();
        }
       return DaySo;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Init(){
        nenTien1 = findViewById(R.id.tv_giatri1);
        nenTien2 = findViewById(R.id.tv_giatri2);
        TienCanDoi = findViewById(R.id.tv_tientrckhichuyen);
        TienSauDoi = findViewById(R.id.tv_tiensaukhichuyen);
        spinner_1 = findViewById(R.id.sp_chuyen1);
        spinner_2= findViewById(R.id.sp_chuyen2);
        chuyen2ben = findViewById(R.id.img_exchange2ben);
        chuyentien = findViewById(R.id.img_doitien);
        mtoolbar = findViewById(R.id.ngoaite_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tien = findViewById(R.id.edt_tiendoi);
        list = new ArrayList<NgoaiTe>();
        list.add(new NgoaiTe("USA","USA dollar",1.0,1.0,R.drawable.usa));
        list.add(new NgoaiTe("VND","Vietnames Dong",0.0000428044,23287.0,R.drawable.vietnam));
        list.add(new NgoaiTe("GBP","British Pound",1.30994,0.763410,R.drawable.anh));
        list.add(new NgoaiTe("JPY","Japanese Yen",0.00888726,112.539,R.drawable.japan));
        list.add(new NgoaiTe("THB","Thai Baht",0.0306787,32.5907,R.drawable.thailand));
        list.add(new NgoaiTe("RUB","Russian Ruble",0.0152543,65.5560,R.drawable.russia));
        list.add(new NgoaiTe("KRW","South Korean Won",0.000879942,1.136,R.drawable.hanquoc));
        list.add(new NgoaiTe("HKD","Hong Kong Dollar",0.127572,7.83886,R.drawable.hongkong));
        list.add(new NgoaiTe("EUR","Euro",1.14840,0.870777,R.drawable.euro));
    }


}
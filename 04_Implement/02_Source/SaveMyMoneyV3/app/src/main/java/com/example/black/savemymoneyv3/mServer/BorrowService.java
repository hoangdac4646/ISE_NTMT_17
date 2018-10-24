package com.example.black.savemymoneyv3.mServer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;
import com.example.black.savemymoneyv3.mClass.DuDinh;
import com.example.black.savemymoneyv3.mFragment.Plan_Fragment;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.black.savemymoneyv3.mClass.App.Channel_ID;

public class BorrowService extends Service {

    private ArrayList<String> Dates;
    @Override
    public void onCreate() {
        super.onCreate();
        Dates = new ArrayList<>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getStringArrayListExtra("dates") != null){
            Dates = intent.getStringArrayListExtra("dates");
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(calendar.getTime());
        int count = 0;
        Intent notificationintent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationintent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(Dates.size() != 0){
            for(int i = 0; i < Dates.size() ; i++){
                if(today.equals(Dates.get(i))){
                    count++;
                }
            }
            Notification notification = new NotificationCompat.Builder(this, Channel_ID)
                    .setContentTitle("Today's Plan")
                    .setContentText("Bạn Có " + count + " dự dịnh cần phải làm trong ngày hôm nay")
                    .setSmallIcon(R.drawable.plan_24)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;

            startForeground(1, notification);
        }

        return START_REDELIVER_INTENT;
    }


    @Nullable
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

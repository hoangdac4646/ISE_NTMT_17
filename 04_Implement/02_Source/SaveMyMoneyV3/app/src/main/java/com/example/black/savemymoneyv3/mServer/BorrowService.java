package com.example.black.savemymoneyv3.mServer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.black.savemymoneyv3.MainActivity;
import com.example.black.savemymoneyv3.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

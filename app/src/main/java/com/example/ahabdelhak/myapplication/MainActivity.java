package com.example.ahabdelhak.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+5000,pendingIntent);


        //to cancel notifi when open APP
        NotificationManager m = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        m.cancel(1);


    }
}

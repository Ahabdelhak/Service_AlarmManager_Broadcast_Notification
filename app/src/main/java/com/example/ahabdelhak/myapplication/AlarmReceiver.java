package com.example.ahabdelhak.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Receiver", Toast.LENGTH_SHORT).show();


        //Intent Service
        Intent in = new Intent(context,MyIntentService.class);
        context.startService(in);


        //JOB SERVICE
//        Intent in=new Intent(context,MyJobService.class);
//        MyJobService.enque(context,in);


    }
}

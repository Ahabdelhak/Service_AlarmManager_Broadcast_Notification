package com.example.ahabdelhak.myapplication;

import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobService extends JobIntentService {


    public static void enque(Context context, Intent in) {
        enqueueWork(context,MyJobService.class,1,in);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i("result", "on job");    }
}

package com.example.ahabdelhak.myapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaSync;
import android.os.Build;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService implements Response.ErrorListener, Response.Listener<String> {

    private static final String CHANNEL_ID = "channel";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url="https://www.findyourfate.com/rss/horoscope-astrology-feed.php?mode=view";
        StringRequest request = new StringRequest(url, this, this);

        queue.add(request);

        Log.i("result","sent request");

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("result","entering onerror");
    }

    @Override
    public void onResponse(String response) {

        Log.i("result","entering onresponse");

        SAXBuilder builder=new SAXBuilder();

        StringReader reader=new StringReader(response);

        try {
            Document document = builder.build(reader);
            List<Element> items= document.getRootElement().getChild("channel")
                    .getChildren("item");

            for (Element item : items) {
                if (item.getChild("title").getText().startsWith("Scorpio")) {
//                        luckText.setText(item.getChild("description").getText());

//                        Intent in=new Intent("com.hanynemr.apidemo.action.myaction");
//                        in.putExtra("luck",item.getChild("description").getText());
//                        sendBroadcast(in);

                    Log.i("result",item.getChild("description").getText());

                    showNotif(item.getChild("description").getText());
                    break;
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showNotif(String description) {
      NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

      Intent intent =new Intent(this,MainActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

      createNotificationChannel();


      NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
      Notification notification = builder.setTicker("new horoscope arrived")
              .setSmallIcon(R.mipmap.ic_launcher)
              .setContentTitle("cancer")
              .setContentText(description.substring(0, 20) + "......")
              .setAutoCancel(true)
              .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
              .setContentIntent(pendingIntent)
              .build();

        manager.notify(1,notification);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel", importance);
            channel.setDescription("my channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}

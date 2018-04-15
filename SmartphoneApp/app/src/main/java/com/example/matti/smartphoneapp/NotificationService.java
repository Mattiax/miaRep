package com.example.matti.smartphoneapp;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import notificationobject.NotificationObject;

/**
 * Created by MATTI on 07/01/2018.
 */

public class NotificationService extends NotificationListenerService {

    Bluetooth bt;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onListenerConnected() {}

    @Override
    public void onListenerDisconnected() {}

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        Bitmap id = sbn.getNotification().largeIcon;

        Log.i("NOTIFICATION","\n\nSEND\n\n");
        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",text);

        byte[] byteArray = null;
        if(id != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        Log.i("Text",sbn.getPostTime()+"");
        NotificationObject no=new NotificationObject(title,text, sbn.getPostTime()+"",byteArray);
        new Bluetooth.SendNotification().execute(no);
        //new SenderBT().execute(no);
        //LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }

    /*public class SenderBT extends AsyncTask<Object,Object,Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            NotificationObject ob;
            try{
                ob  =(NotificationObject)objects[0];
                ConnectedThread.write(ob);
                Log.i("BACKGROUND","WRITTEN");
            }catch(ClassCastException | IOException e){}
            return null;
        }
    }*/
    
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");
    }
}

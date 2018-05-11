package com.example.matti.smartphoneapp;


import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
        String text="";
        try {
            text = extras.getCharSequence("android.text").toString();
        }catch(NullPointerException e){}

        int color=sbn.getNotification().color;
        int id = sbn.getId();
        Bitmap image = sbn.getNotification().largeIcon;


        ApplicationInfo ai;
        try {
            ai = getPackageManager().getApplicationInfo( sbn.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) getPackageManager().getApplicationLabel(ai);
        byte[] byteArray = null;
        if(image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }



        Context remotePackageContext = null;
        byte[] bmp = null;
        try {
            Icon g= sbn.getNotification().getSmallIcon();

            remotePackageContext = getApplicationContext().createPackageContext(pack, 0);
            int idA = extras.getInt(Notification.EXTRA_SMALL_ICON);
            Drawable icon = remotePackageContext.getResources().getDrawable(idA);
            if(icon !=null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ((BitmapDrawable) icon).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                bmp = stream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        NotificationObject no=new NotificationObject(title,text,applicationName==null?"Sconosciuto":applicationName,pack, sbn.getPostTime(),byteArray,bmp,color,id);
        new Bluetooth.SendNotification().execute(no);

        Log.i("NOTIFICATION","\n\nSEND\n\n");
        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",title);
        Log.i("Text",text);
        Log.i("Color",String.format("#%06X", (0xFFFFFF & sbn.getNotification().color)));
        Log.i("Group",applicationName);
    }
    
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");
    }
}

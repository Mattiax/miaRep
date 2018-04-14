package com.example.matti.smartphoneapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import notificationobject.NotificationObject;

/**
 * Created by MATTI on 02/02/2018.
 */

public class NotificationReceiver {

   /* NotificationReceiver(){
        //LocalBroadcastManager.getInstance(c).registerReceiver(new BroadcastReceiverNot(), new IntentFilter("Msg"));
    }

    public class BroadcastReceiverNot extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "incoming not", Toast.LENGTH_SHORT).show();
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, MainActivity.class);
                context.startService(serviceIntent);
            }
            Toast.makeText(context, "Starting broadcast ", Toast.LENGTH_SHORT).show();
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            byte[] byteArray = intent.getByteArrayExtra("icon");
            NotificationObject no=new NotificationObject(title,text, SimpleDateFormat.getDateTimeInstance().toString(),byteArray);
            /*SenderBT sb= new SenderBT();
            sb.execute(no);*/
      /*  }
    }*/

    /*public class SenderBT extends AsyncTask<Object,Object,Object>{

        @Override
        protected Object doInBackground(Object... objects) {
            NotificationObject ob =null;
            try{
                ob  =(NotificationObject)objects[0];
                ConnectedThread.write(ob);
                Log.i("BACKGROUND","WRITTEN");
            }catch(ClassCastException | IOException e){}
            return null;
        }
    }*/
}

package com.example.matti.smartphoneapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

public class BluetoothReceiver extends BroadcastReceiver {

    private boolean isConnected;
    public static Handler handler = new Handler();
   /* Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isConnected) {
                tryReconnection();
                Log.d("RUNNABLE", "RUN");
                handler.postDelayed(runnable, 10000);
            }
        }
    };*/
    ;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BLUETOOTH RECEIVER", intent.getAction());
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        switch (intent.getAction()) {
            case "android.bluetooth.device.action.ACL_DISCONNECTED":
                notifyDisconnection(context);
                isConnected = false;
                //handler=new Handler();
                //handler.post(runnable);
                tryReconnection();
                break;
            case "android.bluetooth.device.action.ACL_CONNECTED":
               // handler.removeCallbacks(runnable);
                break;
            case "android.bluetooth.device.action.FOUND":
                BluetoothDevice deviceTemp = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (deviceTemp.getName() != null && !deviceTemp.getName().isEmpty() && deviceTemp.getName().equals("Svegliamultifunzione")) {
                    tryReconnection();
                }
                break;
            default:
                break;
        }
        /*else if (action.contains("STATE_CHANGED")) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;

                    case BluetoothAdapter.STATE_ON:
                        break;
                }
            }*/
    }

    private void notifyDisconnection(Context c) {
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Notifica alla disconnessione";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(c, CHANNEL_ID)
                    .setContentTitle("Sei disconnesso")
                    .setContentText("Sei disconnesso dalla sveglia a causa della distanza o di un errore imprevisto")
                    .setSmallIcon(R.drawable.ic_bluetooth_disconnected)
                    .setChannelId(CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManager mNotificationManager =
                    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notifyID, notification.build());

        } else {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(c, "")
                    .setSmallIcon(R.drawable.ic_bluetooth_disconnected)
                    .setContentTitle("Sveglia disconnessa")
                    .setContentText("Sei troppo lontano dalla sveglia oppure è nato un problema")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
            notificationManager.notify(notifyID, notification.build());

        }
    }

    public void tryReconnection() {
        new Runnable() {
            @Override
            public void run() {
                Bluetooth.tryReconnection();
            }
        }.run();

    }
}

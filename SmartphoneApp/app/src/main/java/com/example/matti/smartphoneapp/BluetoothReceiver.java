package com.example.matti.smartphoneapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
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

    private boolean isThreadAlive;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BLUETOOTH RECEIVER", intent.getAction());
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        switch (intent.getAction()) {
            case "android.bluetooth.device.action.ACL_DISCONNECTED":
                notifyDisconnection(context);
                if(!isThreadAlive)
                tryReconnection();
                isThreadAlive=true;
                break;
            case "android.bluetooth.device.action.ACL_CONNECTED":
                break;
            case "android.bluetooth.device.action.FOUND":
                BluetoothDevice deviceTemp = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (deviceTemp.getName() != null && !deviceTemp.getName().isEmpty() && deviceTemp.getName().equals("Svegliamultifunzione")) {
                    tryReconnection();
                }
                break;
            case "android.bluetooth.adapter.action.STATE_CHANGED":
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        notifyBluetoothOff(context);
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    private void notifyDisconnection(Context c) {
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Disconnessione";// The user-visible name of the channel.
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

    private void notifyBluetoothOff(Context c) {
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_02";// The id of the channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Bluetooth spento";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(c, CHANNEL_ID)
                    .setContentTitle("Bluetooth spento")
                    .setContentText("Senza bluetooth non riceverai le notifiche sulla sveglia")
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
                    .setContentTitle("Bluetooth spento")
                    .setContentText("Senza bluetooth non riceverai le notifiche sulla sveglia")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
            notificationManager.notify(notifyID, notification.build());

        }
    }

    public void tryReconnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bluetooth.tryReconnection();
                isThreadAlive=false;
            }
        }).start();
    }
}

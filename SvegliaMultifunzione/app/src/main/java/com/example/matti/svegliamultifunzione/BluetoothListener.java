package com.example.matti.svegliamultifunzione;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Bluetooth", intent.getAction());
        switch (intent.getAction()) {
            case "android.bluetooth.device.action.ACL_DISCONNECTED":
                HomePage.disconnected(true);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Bluetooth.enableServerSocket();
                    }
                }).start();
                break;
            case "android.bluetooth.device.action.ACL_CONNECTED":
                HomePage.disconnected(false);
                new NotificationService(HomePage.getHandler());
                break;
            default:
                break;
        }
    }
}

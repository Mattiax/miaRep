package com.example.matti.smartphoneapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BLUETOOTH RECEIVER",intent.getAction());
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
    }

}

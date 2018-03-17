package com.example.matti.smartphoneapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Set;

import notificationobject.NotificationObject;

/**
 * Created by MATTI on 07/02/2018.
 */

public class ConnectedThread extends Thread {

    private static ObjectOutputStream outputStream;
    private BluetoothAdapter bluetooth;
    protected BluetoothSocket socket;

    ConnectedThread(BluetoothSocket socket) throws IOException {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        this.socket=socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(NotificationObject no) throws IOException {
        outputStream.writeObject(no);
        outputStream.flush();
        outputStream.reset();
        Log.i("THREAD socket","WRITTEN");
    }
}

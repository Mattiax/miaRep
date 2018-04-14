package com.example.matti.smartphoneapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import notificationobject.NotificationObject;

public class Bluetooth {

    private static BluetoothAdapter adapter;
    private static BluetoothSocket socket;
    private static ObjectOutputStream outputStream;

    public Bluetooth(Context context) {
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothAdapter getAdapter() {
        return adapter;
    }

    public static BluetoothSocket getSocket() {
        return socket;
    }

    public static void setSocket(BluetoothDevice device) throws IOException {
        socket = device.createRfcommSocketToServiceRecord(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));

    }

    public static void setOutputStream() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public static class SendNotification extends AsyncTask {

        @Override
        protected Object doInBackground(Object... objects) {
            NotificationObject o=(NotificationObject)objects[0];
            try {
                Log.d("OUTPUT","PRINT");
                outputStream.writeObject(o);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

package com.example.matti.smartphoneapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
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
    private static BluetoothDevice master;

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
        master=device;
        socket = master.createRfcommSocketToServiceRecord(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
    }

    public static boolean tryReconnection() {
        try {
            Log.d("Bluetooth","ENTER");
            socket = master.createRfcommSocketToServiceRecord(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
            socket.connect();
            Log.d("Bluetooth","Connected");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            return true;
        }catch(NullPointerException | IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void setOutputStream() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public static class SendNotification extends AsyncTask<Object,Void,Void> {

        @Override
        protected Void doInBackground(Object... objects) {
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

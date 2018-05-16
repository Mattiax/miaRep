package com.example.matti.svegliamultifunzione;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by MATTI on 23/02/2018.
 */

public class Bluetooth extends Service {

    private static BluetoothAdapter adapter;
    private static BluetoothServerSocket serverSocket;
    private static BluetoothSocket socket;

    public Bluetooth(){
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void setServerSocket(Context c) throws IOException {
        adapter.setName(c.getString(R.string.app_name));
        Log.d("Bluetooth","setServerSocket");
        serverSocket = adapter.listenUsingRfcommWithServiceRecord(c.getString(R.string.app_name), UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
    }

    public static BluetoothAdapter getAdapter() {
        return adapter;
    }

    public static BluetoothSocket getSocket() {
        return socket;
    }

    public static void setVisible(Context c){adapter.setName(c.getString(R.string.app_name)); }

    public static void enableServerSocket() {
        Log.d("PROVAAAAAAAAAAAAA", "THREADDDDDDDDDDDD");
        try {
            socket = serverSocket.accept();
            Log.d("PROVAAAAAAAAAAAAA", "connesso");
            //new NotificationService(HomePage.getHandler());
        } catch (IOException e) {
            Log.d("Bluetooth", "errore");
            e.printStackTrace();
        }
    /*public static class Connect extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... booleans) {

                Log.d("PROVAAAAAAAAAAAAA", "THREADDDDDDDDDDDD");
                socket = serverSocket.accept();
                Log.d("PROVAAAAAAAAAAAAA", "connesso");

                Log.d("PROVAAAAAAAAAAAAA", "fatto");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }*/
    }
}

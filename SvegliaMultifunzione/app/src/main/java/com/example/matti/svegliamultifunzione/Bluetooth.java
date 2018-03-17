package com.example.matti.svegliamultifunzione;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by MATTI on 23/02/2018.
 */

public class Bluetooth {

    private static BluetoothAdapter adapter;
    private static BluetoothServerSocket serverSocket;
    private static BluetoothSocket socket;

    public Bluetooth(Context c) throws IOException {
        adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.setName(c.getString(R.string.app_name));
        serverSocket = adapter.listenUsingRfcommWithServiceRecord(c.getString(R.string.app_name), UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
    }

    public static BluetoothAdapter getAdapter() {
        return adapter;
    }

    public static BluetoothSocket getSocket() {
        return socket;
    }

    public static class Connect extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            try {
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
    }
}

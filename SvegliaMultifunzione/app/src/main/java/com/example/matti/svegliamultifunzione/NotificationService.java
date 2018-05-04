package com.example.matti.svegliamultifunzione;

import android.app.LocalActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import notificationobject.NotificationObject;

import static com.example.matti.svegliamultifunzione.Bluetooth.getSocket;

/**
 * Created by MATTI on 06/02/2018.
 */

public class NotificationService extends Observable implements Runnable {

    private ObjectInputStream inStream;

    public NotificationService() {
        try {
            inStream = new ObjectInputStream(Bluetooth.getSocket().getInputStream());
            AsyncTask.execute(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Log.d("Notification", "RUNNINGGGGGGGGGGGGGGGGGGGG");
        while (true) {
            try {
                try {
                    NotificationObject o = (NotificationObject) inStream.readObject();
                    setChanged();
                    notifyObservers(o);
                    Log.d("NOTIFICAAAAAAAA", "ARIVATAAAAAAAA");
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}

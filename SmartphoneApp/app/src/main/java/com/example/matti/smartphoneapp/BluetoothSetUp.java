package com.example.matti.smartphoneapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothSetUp extends AppCompatActivity {

    private ListView list;
    private static final int REQUEST_ENABLE_BT = 1;
    private List<BluetoothDevice> bluetoothScan;
    private BluetoothAdapter bluetooth;
    private MyAdapter adapter;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_set_up);
        list = findViewById(R.id.listB);
        bar = findViewById(R.id.progressBar);
        bluetoothScan = new ArrayList<>();
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        adapter = new MyAdapter(BluetoothSetUp.this, R.id.listB,bluetoothScan);
        list.setAdapter(adapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(receiver, filter);

        if (bluetooth.isEnabled()) {
            bluetooth.startDiscovery();
        } else {
            setUpConnection();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Method method = null;
                try {
                    bluetooth.cancelDiscovery();
                    method = bluetoothScan.get(i).getClass().getMethod("createBond", (Class[]) null);
                    method.invoke(bluetoothScan.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BluetoothSetUp.this, "fail " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpConnection() {
        if (!bluetooth.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                bar.setVisibility(View.VISIBLE);
                bluetoothScan.clear();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                bar.setVisibility(View.INVISIBLE);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    try {
                        bluetooth.cancelDiscovery();
                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"));
                        socket.connect();
                        Toast.makeText(context, "Connesso a: " + socket.getRemoteDevice().getName(), Toast.LENGTH_SHORT).show();
                        new ConnectedThread(socket);
                        new NotificationReceiver(getApplicationContext());
                        startActivity(new Intent(BluetoothSetUp.this, MainActivity.class));
                        finish();
                    } catch (IOException e) {
                        Toast.makeText(context, "broad "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                bluetoothScan.add((BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                adapter.clear();
                adapter.addAll(bluetoothScan);
                adapter.notifyDataSetChanged();
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
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}



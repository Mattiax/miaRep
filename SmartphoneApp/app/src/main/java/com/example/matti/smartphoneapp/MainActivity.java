package com.example.matti.smartphoneapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_NOTIFICATION_LISTENER_SETTINGS = 0, REQUEST_POSITION_ACCESS=1;
    private ImageView permissionDenied;
    private Animation anim;
   // private BluetoothAdapter bluetooth;
    private Button newDevice;
    private TextView needConf;
//fai una classe bluetooth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Bluetooth(this);
        setContentView(R.layout.activity_main);
        newDevice=findViewById(R.id.addDevice);
        needConf=findViewById(R.id.needConf);
        permissionDenied = findViewById(R.id.permissionDenied);
        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        permissionDenied.setVisibility(View.INVISIBLE);
        checkLocationPermission();
        if (!isNotReadable()) {
            askForPermission();
        }else{
            startConnection();
        }
        permissionDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
            }
        });

        newDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,BluetoothSetUp.class));
            }
        });
    }

    void checkLocationPermission(){
        if (Build.VERSION.SDK_INT >= 23) { // from Android 6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_POSITION_ACCESS);
            }
        }
    }

    void askForPermission() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.need_permission))
                .setMessage(getString(R.string.need_notification_permission))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                        startActivityForResult(intent, REQUEST_NOTIFICATION_LISTENER_SETTINGS);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean isNotReadable() {
        try {
            if (Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void startConnection(){
        BluetoothAdapter bt = Bluetooth.getAdapter();
        Object[] paired = bt.getBondedDevices().toArray();
        BluetoothDevice device = null;
        for (int i = 0; i < paired.length; i++) {
            device = (BluetoothDevice) paired[i];
            if (device.getName().contains("SvegliaMultifunzione")) {
                break;
            }
        }

        if(device!=null&&device.getName().equals("SvegliaMultifunzione")) {
            needConf.setText(getResources().getString(R.string.sync_complete));
            try {
               //new NotificationReceiver(getApplicationContext());
                Bluetooth.setSocket(device);
                BluetoothSocket socket = Bluetooth.getSocket();
                socket.connect();
                Bluetooth.setOutputStream();
                Toast.makeText(MainActivity.this, "Connesso " + socket.getRemoteDevice().getName(), Toast.LENGTH_SHORT).show();
                //new ConnectedThread(socket);
                //socket.close();
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
                //finish();
                new BluetoothReceiver();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "failMain\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            newDevice.setVisibility(View.VISIBLE);
            needConf.setText(getResources().getString(R.string.need_sync));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case REQUEST_NOTIFICATION_LISTENER_SETTINGS:
                if (!isNotReadable()) {
                    permissionDenied.setAnimation(anim);
                    anim.start();
                    permissionDenied.setVisibility(View.VISIBLE);
                    askForPermission();
                } else {
                    permissionDenied.setVisibility(View.INVISIBLE);
                    permissionDenied.clearAnimation();
                    startConnection();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_POSITION_ACCESS){
            if(grantResults[0]  != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

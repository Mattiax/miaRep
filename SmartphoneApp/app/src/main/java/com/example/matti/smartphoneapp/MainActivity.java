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
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_NOTIFICATION_LISTENER_SETTINGS = 0, REQUEST_POSITION_ACCESS = 1, REQUEST_ENABLE_BT = 2;
    private ImageView permissionDenied,image_status;
    private Animation anim;
    private Button newDevice;
    private TextView  status;
    private ProgressBar connectingBar;
    static Context c;
    private BluetoothReceiver receiver;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Bluetooth(this);
        setContentView(R.layout.activity_main);
        newDevice = findViewById(R.id.addDevice);
        status = findViewById(R.id.device_status);
        layout = findViewById(R.id.main_activity_lay);
        image_status= findViewById(R.id.image_status);
        connectingBar = findViewById(R.id.automatic_connection_bar);
        permissionDenied = findViewById(R.id.permissionDenied);
        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        permissionDenied.setVisibility(View.INVISIBLE);
        checkLocationPermission();
        c = this;
        permissionDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
            }
        });
        newDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BluetoothSetUp.class));
            }
        });
        if (!isNotReadable()) {
            askForPermission();
        }
        if(Bluetooth.getAdapter().isEnabled()){
            startConnection();
        }else{
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    public static void d(Bitmap m) {
        android.app.AlertDialog.Builder alertadd = new android.app.AlertDialog.Builder(c);
        LayoutInflater factory = LayoutInflater.from(c);
        final View view = factory.inflate(R.layout.a, null);
        ImageView a = view.findViewById(R.id.dialog_imageview);
        Log.d("bitmap", m.getNinePatchChunk().toString());
        a.setImageBitmap(m);
        alertadd.setView(view);
        alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {

            }
        });

        alertadd.show();
    }

    void checkLocationPermission() {
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
            return Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void startConnection() {
        BluetoothAdapter bt = Bluetooth.getAdapter();
        Object[] paired = bt.getBondedDevices().toArray();
        BluetoothDevice device = null;
        for (int i = 0; i < paired.length; i++) {
            device = (BluetoothDevice) paired[i];
            if (device.getName().contains("SvegliaMultifunzione")) {
                connectingBar.setVisibility(View.VISIBLE);
                break;
            }
        }

        if (device != null && device.getName().equals("SvegliaMultifunzione")) {
            try {
                Bluetooth.setSocket(device);
                final BluetoothSocket socket = Bluetooth.getSocket();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket.connect();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    connectingBar.setVisibility(View.INVISIBLE);
                                    status.setText(getString(R.string.sync_complete));
                                    Toast.makeText(MainActivity.this, "Connesso " + socket.getRemoteDevice().getName(), Toast.LENGTH_SHORT).show();
                                    IntentFilter filter = new IntentFilter();
                                    filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
                                    filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
                                    filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                                    filter.addAction(BluetoothDevice.ACTION_FOUND);
                                    receiver = new BluetoothReceiver();
                                    registerReceiver(receiver, filter);
                                    layout.setBackgroundResource(R.drawable.gradient_con_anim);
                                    AnimationDrawable layoutAnim = (AnimationDrawable) layout.getBackground();
                                    layoutAnim.setAlpha(150);
                                    layoutAnim.setEnterFadeDuration(1500);
                                    layoutAnim.setExitFadeDuration(3000);
                                    layoutAnim.start();
                                    image_status.setImageResource(R.drawable.ic_device_connected);
                                }
                            });
                            Bluetooth.setOutputStream();
                        } catch (final IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "failMain\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    connectingBar.setVisibility(View.INVISIBLE);
                                    status.setText(getString(R.string.error_find));
                                    image_status.setImageResource(R.drawable.ic_connection_fail);
                                    startErrorBackground();
                                    //handler che riprova
                                }
                            });
                        }
                    }
                }).start();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "failMain\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                //errore grame
            }
        } else {
            connectingBar.setVisibility(View.INVISIBLE);
            newDevice.setVisibility(View.VISIBLE);
            status.setText(getString(R.string.need_sync));
            image_status.setImageResource(R.drawable.ic_no_device);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
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
            case REQUEST_ENABLE_BT:
                if (Bluetooth.getAdapter().isEnabled()) {
                    startConnection();
                } else {
                    startErrorBackground();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_POSITION_ACCESS) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Activity destroyed", Toast.LENGTH_LONG).show();
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    private BroadcastReceiver broadTemp = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
    };

    private void startErrorBackground(){
        layout.setBackgroundResource(R.drawable.gradient_error_anim);
        AnimationDrawable layoutAnim = (AnimationDrawable) layout.getBackground();
        layoutAnim.setAlpha(200);
        layoutAnim.setEnterFadeDuration(1000);
        layoutAnim.setExitFadeDuration(2000);
        layoutAnim.start();
    }
}

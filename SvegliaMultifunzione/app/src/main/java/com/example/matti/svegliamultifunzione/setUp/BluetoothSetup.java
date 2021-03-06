package com.example.matti.svegliamultifunzione.setUp;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.Bluetooth;
import com.example.matti.svegliamultifunzione.HomePage;
import com.example.matti.svegliamultifunzione.R;

import java.io.IOException;

import static com.example.matti.svegliamultifunzione.Bluetooth.getAdapter;

/**
 * Created by MATTI on 28/12/2017.
 */

public class BluetoothSetup extends Fragment {

    ProgressBar waiting;
    private ConstraintLayout lay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        getContext().registerReceiver(receiver, filter);
        new Bluetooth();
        if (getAdapter().isEnabled()) {
            Log.d("Bluetooth","ENABLED");
            setDiscoverable();
        } else {
            Log.d("Bluetooth","NOT ENABLED");
            getAdapter().enable();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bluetooth_setup, container, false);
        waiting = root.findViewById(R.id.waitingBar);
        lay = root.findViewById(R.id.bluetooth_set_up);
        ImageView bluetooth = root.findViewById(R.id.bluetooth_blt_setup);
        ((AnimationDrawable) bluetooth.getBackground()).start();
        AnimationDrawable layout = (AnimationDrawable) lay.getBackground();
        layout.setAlpha(122);
        layout.setEnterFadeDuration(2000);
        layout.setExitFadeDuration(4000);
        layout.start();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

      /*  try{

        Bluetooth.setServerSocket(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //try {

        //new V().start();

       /* } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*if (!getAdapter().isEnabled()) {
            getAdapter().enable();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
        }*/
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("Bluetooth", action);
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    try {
                        waiting.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d("Bluetooth", "off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d("Bluetooth", "toff");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d("Bluetooth", "on");
                        setDiscoverable();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d("Bluetooth", "ton");
                        break;
                }
            }
        }
    };

    class V extends Thread {

        @Override
        public void run() {
            try {
                //boolean b= new Bluetooth.Connect().execute().get();
                Bluetooth.enableServerSocket();
                Log.d("THREAD", "CONNESSO");
                Intent homePage = new Intent(getContext(), HomePage.class);
                Bundle cap = new Bundle();
                cap.putString("cap", getActivity().getPreferences(Context.MODE_PRIVATE).getString("cap", ""));
                homePage.putExtras(cap);
                startActivity(homePage);
                getActivity().finish();
                currentThread().interrupt();
                //Log.d("RIS",""+b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDiscoverable() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 600);
        startActivity(discoverableIntent);
        try {
            Bluetooth.setServerSocket(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new V()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }
}

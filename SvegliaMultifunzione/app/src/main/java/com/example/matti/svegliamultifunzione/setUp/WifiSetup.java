package com.example.matti.svegliamultifunzione.setUp;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATTI on 28/12/2017.
 */

public class WifiSetup extends Fragment {

    WifiManager manager;
    ConnectivityManager connManager;
    NetworkInfo nState;
    TextView connected;
    View rootView;
    List<ScanResult> temp;
    ListView wifiList;
    WiFiAdapter adapter;
    ProgressBar wifiScan;
    IntentFilter intentFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        connManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.EXTRA_SUPPLICANT_CONNECTED);
        getContext().registerReceiver(reciver, intentFilter);

        if (!manager.isWifiEnabled()) {
            askForWifi();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wifi_setup, container, false);
        connected = rootView.findViewById(R.id.connectedTxt);
        wifiList = rootView.findViewById(R.id.wifiList);
        wifiScan= rootView.findViewById(R.id.scanning);
        connected.setVisibility(View.INVISIBLE);
        adapter = new WiFiAdapter(getActivity(), R.layout.wifi_setup);
        wifiList.setAdapter(adapter);
        wifiScan.setVisibility(View.VISIBLE);
        wifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                connectToWiFi(temp.get(i).SSID);
            }
        });
        if(!connManager.getActiveNetworkInfo().isConnected()){
            wifiScan.setVisibility(View.INVISIBLE);
        }else{
            wifiScan.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    public void connectToWiFi(final String ssid){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Password");
        builder.setView(input);
        builder.setTitle(getString(R.string.connect_wifi))
                .setMessage(ssid)
                .setPositiveButton("Connetti", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        WifiConfiguration conf = new WifiConfiguration();
                        conf.SSID = "\"" + ssid + "\"";
                        conf.preSharedKey = "\""+ input.getText().toString() +"\"";
                        WifiManager wifiManager = (WifiManager)getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.addNetwork(conf);
                        manager.disconnect();
                        manager.enableNetwork(conf.networkId,true);
                        manager.reconnect();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private final BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
            if (!manager.isWifiEnabled()) {
                askForWifi();
            } else {
                nState = connManager.getActiveNetworkInfo();
                if (nState == null||!nState.isConnected()) {
                    connected.setVisibility(View.INVISIBLE);
                    temp = manager.getScanResults();
                    wifiScan.setVisibility(View.INVISIBLE);
                    adapter.addAll(temp);
                    adapter.notifyDataSetChanged();
                } else if (nState.isConnected()) {
                    getContext().unregisterReceiver(this);
                    connected.setVisibility(View.VISIBLE);
                    connected.setText(connected.getText().toString() + " " + manager.getConnectionInfo().getSSID());
                }
            }

        }
    };

    void askForWifi() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.wifi_permission))
                .setMessage(getString(R.string.application_need_wifi))
                .setPositiveButton("Attiva", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
                        manager.setWifiEnabled(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        connected.setVisibility(View.VISIBLE);
                        connected.setText(getString(R.string.application_need_wifi));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}

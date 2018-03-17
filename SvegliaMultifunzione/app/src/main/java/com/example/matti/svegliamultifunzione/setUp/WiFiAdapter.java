package com.example.matti.svegliamultifunzione.setUp;

import android.bluetooth.le.ScanRecord;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matti.svegliamultifunzione.R;

/**
 * Created by MATTI on 12/03/2018.
 */

public class WiFiAdapter extends ArrayAdapter {

    public WiFiAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater;
            inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.wifi_adapter,null);
        }
        ScanResult o=(ScanResult) getItem(i);
        TextView name=view.findViewById(R.id.ssid);
        name.setText(((ScanResult)getItem(i)).SSID);
        return view;
    }
}

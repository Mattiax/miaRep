package com.example.matti.smartphoneapp;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MATTI on 01/02/2018.
 */

public class MyAdapter extends ArrayAdapter {

    List<BluetoothDevice> list;

    public MyAdapter(@NonNull Context context, int resource, List<BluetoothDevice> list) {
        super(context, resource);
        this.list = list;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.bluetooth_list, null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.bluetoothName);
            viewHolder.image = view.findViewById(R.id.bIcon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BluetoothDevice bd = list.get(i);
        viewHolder.name.setText(bd.getName());
        viewHolder.image.setImageResource(R.drawable.bluetooth);
        return view;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView name;
    }
}

package com.example.matti.svegliamultifunzione;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import notificationobject.NotificationObject;

/**
 * Created by MATTI on 08/02/2018.
 */

public class AdapterApp extends ArrayAdapter {

    List<ApplicationInfo> appList;
    PackageManager pm;

    public AdapterApp(@NonNull Context context, int resource,PackageManager pm, List<ApplicationInfo> appList) {
        super(context, resource);
        this.appList = appList;
        this.pm=pm;
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
        ViewHolder viewHolder;

        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_app, null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.appName);
            viewHolder.image = view.findViewById(R.id.appIcon);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ApplicationInfo ai = appList.get(i);
        viewHolder.name.setText(ai.name);
        viewHolder.image.setImageDrawable(ai.loadIcon(pm));
        return view;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView name;
    }
}

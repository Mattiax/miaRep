package com.example.matti.svegliamultifunzione;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import notificationobject.NotificationObject;

/**
 * Created by MATTI on 08/02/2018.
 */

public class Adapter extends ArrayAdapter {

    List<NotificationObject> list;

    public Adapter(@NonNull Context context, int resource, List<NotificationObject> list) {
        super(context, resource);
        this.list=list;
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

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.notification_item,null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.ApplicationName);
            viewHolder.message = view.findViewById(R.id.ApplicationMessage);
            viewHolder.image = view.findViewById(R.id.ApplicationIcon);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        NotificationObject o= list.get(i);
        TextView data=view.findViewById(R.id.ApplicationDataReceive);
        viewHolder.name.setText(o.getName());
        //viewHolder.name.setTextColor(o.getAppColor());
        String hexColor = String.format("#%06X", (0xFFFFFF & o.getAppColor()));
        viewHolder.message.setText(o.getMessage());
        Calendar cal= Calendar.getInstance();
        Log.d("TIME",o.getTime());
        data.setText(o.getTime());
        if(o.getIcon()!=null) {
            viewHolder.image.setImageBitmap( BitmapFactory.decodeByteArray(o.getIcon(), 0, o.getIcon().length));
        }else{
            viewHolder.image.setImageResource(R.drawable.new_notification_common_icon);
        }
        return view;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView message;
    }
}

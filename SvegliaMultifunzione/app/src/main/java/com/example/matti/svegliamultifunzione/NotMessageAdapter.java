package com.example.matti.svegliamultifunzione;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import notificationobject.NotificationObject;

public class NotMessageAdapter extends ArrayAdapter {

    public List<NotificationObject> messages;

    public NotMessageAdapter(@NonNull Context context, int resource,List<NotificationObject> messages) {
        super(context, resource);
        this.messages=messages;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.single_not_view,null);
            viewHolder = new ViewHolder();
            viewHolder.image = view.findViewById(R.id.single_not_image);
            viewHolder.message = view.findViewById(R.id.single_message);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        NotificationObject o= messages.get(i);
        if(o.getIcon()!=null) {
            viewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(o.getIcon(), 0, o.getIcon().length));
        }
        viewHolder.message.setText(o.getMessage());
        return view;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView message;
    }
}

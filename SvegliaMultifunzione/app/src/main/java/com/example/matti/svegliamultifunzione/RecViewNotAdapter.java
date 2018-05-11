package com.example.matti.svegliamultifunzione;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import notificationobject.NotificationObject;

public class RecViewNotAdapter extends RecyclerView.Adapter<RecViewNotAdapter.ViewHolder> {

    private LinkedHashMap<String,List<NotificationObject>> list;//List<NotificationObject> not;
    private static Context context;

    public RecViewNotAdapter(Context context, LinkedHashMap<String,List<NotificationObject>> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecViewNotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout
        Log.d("NUMERO",""+viewType);
        View contactView = inflater.inflate(R.layout.group_not_view, parent, false);
        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(RecViewNotAdapter.ViewHolder viewHolder, int i) {

        //String [] header =new ArrayList<>(list.keySet()).get(i);
        List<NotificationObject> notList=new ArrayList<>(list.values()).get(i);
        NotificationObject sample=notList.get(0);
        Log.d("package app", new ArrayList<>(list.keySet()).get(i));
        Log.d("not app", new ArrayList<>(list.values()).get(i).toString());

            viewHolder.appName.setText(sample.getAppName());
            String a="";
        if (sample.getAppIcon() != null) {
                Log.d("IMAGE","OK");
                viewHolder.image.setImageBitmap(BitmapFactory.decodeByteArray(sample.getAppIcon(), 0, sample.getAppIcon().length));
                a+= Base64.encodeToString(sample.getAppIcon(),Base64.NO_WRAP);
            } else {
                Log.d("IMAGE","NOT NULL");
                viewHolder.image.setImageResource(R.drawable.new_notification_common_icon);
            }


            viewHolder.adapter = new NotMessageAdapter(context, R.layout.activity_home_page, notList);
            viewHolder.adapter.addAll(viewHolder.messages);
            viewHolder.messageList.setAdapter(viewHolder.adapter);
            viewHolder.adapter.notifyDataSetChanged();
           /* if (!viewHolder.packageName.isEmpty()) {

        }else{
                viewHolder.packageName = no.getPack();
            }*/
        //viewHolder.author.setText(art.isNull("author") ? "" : art.getString("author"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView appName;
        public ImageView image;
        private ListView messageList;
        private NotMessageAdapter adapter;
        private List<OggettoMessaggio> messages;

        private ViewHolder(View v) {
            super(v);
            appName = v.findViewById(R.id.group_app_name);
            messageList = v.findViewById(R.id.group_message_list);
            image = v.findViewById(R.id.group_image);
            messageList = v.findViewById(R.id.group_message_list);
            messages = new ArrayList<>();
        }
    }
}
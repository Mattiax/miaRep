package com.example.genji.am104_gson;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by studente on 24/03/18.
 */

public class Adapter extends ArrayAdapter<Round> {

    Context c;
    ArrayList<Round> list;

    public Adapter(@NonNull Context context, int resource, ArrayList<Round> list) {
        super(context, resource, list);
        this.c=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        // reuse view: ViewHolder pattern
        if (rowView == null) {
            LayoutInflater inflater =LayoutInflater.from(c);
            rowView = inflater.inflate(R.layout.group, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.name = rowView.findViewById(R.id.groupText);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        //Log.d("match",list.get(position).getName());
        viewHolder.name.setText(list.get(position).getName());
        return rowView;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView description;
    }
}

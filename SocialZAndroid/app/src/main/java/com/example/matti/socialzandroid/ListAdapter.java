package com.example.matti.socialzandroid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ListAdapter extends BaseExpandableListAdapter {

    private Context c;
    private LinkedHashMap<String,List<String>> map;
    private String hobby;

    public ListAdapter(Context c, LinkedHashMap<String,List<String>> map,String hobby) {
        this.c = c;
        this.map = map;
        this.hobby=hobby;
    }

    @Override
    public int getGroupCount() {
        return map.keySet().size();
    }

    @Override
    public int getChildrenCount(int i) {
        return map.get(getGroup(i).toString()).size();
    }

    @Override
    public Object getGroup(int i) {
        return map.keySet().toArray()[i].toString();
    }

    @Override
    public Object getChild(int i, int i1) {
        return map.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group, null);
        }
        TextView hobby = view.findViewById(R.id.hobby);
        hobby.setText(this.hobby==null?getGroup(i).toString():this.hobby);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child, null);
            viewHolder = new ViewHolder();
            viewHolder.childView = view.findViewById(R.id.hobbyList);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.childView.setText(map.get(getGroup(i).toString()).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class ViewHolder {
        TextView childView;
    }
}

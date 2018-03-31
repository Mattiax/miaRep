package com.example.genji.am104_gson;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by studente on 24/03/18.
 */

public class AdapterList extends BaseExpandableListAdapter {

    private Context c;
    private ArrayList<Round> list;
    private String squadra;

    AdapterList(Context c, ArrayList<Round> list, String squadra) {
        this.c = c;
        this.list = list;
        this.squadra = squadra;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {//2
        Log.d("getChildrenCount", "" + list.get(i).getMatches().size());
        return list.get(i).getMatches().size();
    }

    @Override
    public Object getGroup(int i) {
        Log.d("getGroup", "i " + i + " list i " + list.get(i).getMatches());
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        Log.d("getChild", "i " + i + " i1 " + i1 + " list matches i " + list.get(i).getMatches().get(i1));
        return i1;
    }

    @Override
    public long getGroupId(int i) {//1
        Log.d("getGroupId", "" + i);
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        Log.d("getChildId", "i " + i + " i1 " + i1);
        return i1;
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
        TextView txt = view.findViewById(R.id.groupText);
        txt.setText(list.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.team1 = view.findViewById(R.id.team1);
            viewHolder.score1 = view.findViewById(R.id.score1);
            viewHolder.team2 = view.findViewById(R.id.team2);
            viewHolder.score2 = view.findViewById(R.id.score2);
            viewHolder.img = view.findViewById(R.id.imageItem);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        int score1 = list.get(i).getMatches().get(i1).getScore1();
        int score2 = list.get(i).getMatches().get(i1).getScore2();
        if (squadra.equals(list.get(i).getMatches().get(i1).getTeam1().getKey())) {
            viewHolder.team1.setTextColor(Color.GREEN);
            viewHolder.score1.setTextColor(Color.GREEN);
            if (score1 > score2) {
                viewHolder.img.setImageResource(R.drawable.champion);
            }else{
                viewHolder.img.setImageResource(R.drawable.ball);
            }
        } else if (squadra.equals(list.get(i).getMatches().get(i1).getTeam2().getKey())) {
            viewHolder.team2.setTextColor(Color.GREEN);
            viewHolder.score2.setTextColor(Color.GREEN);
            if (score2 > score1) {
                viewHolder.img.setImageResource(R.drawable.champion);
            }else{
                viewHolder.img.setImageResource(R.drawable.ball);
            }
        }else{
            viewHolder.team1.setTextColor(Color.BLACK);
            viewHolder.team2.setTextColor(Color.BLACK);
            viewHolder.score1.setTextColor(Color.BLACK);
            viewHolder.score2.setTextColor(Color.BLACK);
            viewHolder.img.setImageResource(R.drawable.ball);
        }
        viewHolder.team1.setText(list.get(i).getMatches().get(i1).getTeam1().getName());
        viewHolder.team2.setText(list.get(i).getMatches().get(i1).getTeam2().getName());
        viewHolder.score1.setText(String.valueOf(score1));
        viewHolder.score2.setText(String.valueOf(score2));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class ViewHolder {
        ImageView img;
        public TextView team1;
        public TextView score1;
        public TextView team2;
        public TextView score2;
    }
}

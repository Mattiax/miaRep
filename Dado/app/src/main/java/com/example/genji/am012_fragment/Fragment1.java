package com.example.genji.am012_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by genji on 1/29/16.
 */
public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_one, container, false);
        TextView a=view.findViewById(R.id.number);
        Log.d("NumeroRicevuto",getArguments()!=null?String.valueOf(getArguments().getInt("valore")):"Errore");
        a.setText(getArguments()!=null?String.valueOf(getArguments().getInt("valore")):"Errore");

        return view;
    }

    public static Fragment getFragment(){
        Bundle b=new Bundle();
        b.putInt("valore",(int)(Math.random()*10));
        Fragment1 f=new Fragment1();
        f.setArguments(b);
        return f;
    }
}

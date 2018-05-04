package com.example.matti.svegliamultifunzione.setUp;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.example.matti.svegliamultifunzione.R;
import com.example.matti.svegliamultifunzione.weather.WeatherFrag;

/**
 * Created by MATTI on 28/12/2017.
 */

public class WeatherSetUp extends Fragment {

    private EditText cap;
    private Button btn;
    WeatherFrag frag ;
    //private WeatherFrag frag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("weathersetup","onStart");
        frag = new WeatherFrag();
        getFragmentManager().beginTransaction().replace(R.id.weatherSetup, frag).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_setup, container, false);
        cap = v.findViewById(R.id.cap);
        btn = v.findViewById(R.id.confirmCap);
        ConstraintLayout layout = v.findViewById(R.id.weather_layout);
        layout.setBackgroundResource(R.drawable.animated_weather);
        AnimationDrawable an = (AnimationDrawable) layout.getBackground();
        an.setEnterFadeDuration(2000);
        an.setExitFadeDuration(4000);
        an.start();
        return v;
    }

    @Override
    public void onStart() {
        Log.d("weathersetup","onStart");
        super.onStart();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cap.getText().toString().length() > 0) {
                    Log.d("weathersetup","click");
                    /*Bundle b= new Bundle();
                    b.putString("cap","30173");
                    frag.setArguments(b);*/
                    frag.execute(cap.getText().toString());
                    //getFragmentManager().beginTransaction().replace(R.id.map,new MapFrag()).commit();
                }
            }
        });
    }
}

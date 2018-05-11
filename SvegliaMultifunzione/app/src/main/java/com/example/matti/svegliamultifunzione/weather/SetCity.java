package com.example.matti.svegliamultifunzione.weather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.R;

public class SetCity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.no_weather_conf, container, false);
        TextView txt=v.findViewById(R.id.no_city);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "In fase di implementazione", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

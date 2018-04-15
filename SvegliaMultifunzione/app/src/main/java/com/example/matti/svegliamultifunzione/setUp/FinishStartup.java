package com.example.matti.svegliamultifunzione.setUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matti.svegliamultifunzione.R;
import com.example.matti.svegliamultifunzione.weather.Function;

/**
 * Created by MATTI on 28/12/2017.
 */

public class FinishStartup extends Fragment {

    TextView city, weatherIcon, temp, hum, tempMin, tempMax;
    Function.placeIdTask asyncTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.finish_startup, container, false);
        final EditText cap=v.findViewById(R.id.cap);
        Button btn = v.findViewById(R.id.confirmCap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cap.getText().toString().length()>0){
                    initializeAT();
                    asyncTask.execute("30173", "it");
                }
            }
        });
        return v;
    }

    private void initializeAT(){
        asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String sCity, String description, String temperature, String humidity, String weather_pressure, String pdatedOn, String iconText, String sun_rise, String tempMini,String tempMaxi) {
                city.setText(sCity);
                temp.setText(temperature);
                hum.setText("Humidity: " + humidity);
                weatherIcon.setText(Html.fromHtml(iconText));
                tempMin.setText(tempMini);
                tempMax.setText(tempMaxi);

            }
        });
    }
}

package com.example.matti.svegliamultifunzione.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matti.svegliamultifunzione.R;

import java.util.Map;

public class WeatherFrag extends Fragment implements Function.AsyncResponse{

    private TextView city, weatherIcon, temp, hum, tempMin, tempMax, sunset, sunrise, pressure, update;
    private Context c;
    private SharedPreferences pref;


    @Override
    public void onCreate(@Nullable Bundle b) {
        super.onCreate(b);
        Log.d("WeatherFrag","onCreate");
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        try {
            Map<String, ?> a=pref.getAll();
            String cap = pref.getString("cap","");
            Log.d("CAP",cap);
            new Function.placeIdTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,cap,"it");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("WeatherFrag","onCreateView");
        View v = inflater.inflate(R.layout.frame_weather, container, false);
        c = getContext();
        city = v.findViewById(R.id.city);
        weatherIcon = v.findViewById(R.id.weatherIcon);
        weatherIcon.setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/weathericons-regular-webfont.ttf"));
        temp = v.findViewById(R.id.temperature);
        hum = v.findViewById(R.id.humidity);
        tempMin = v.findViewById(R.id.tempMin);
        tempMax = v.findViewById(R.id.tempMax);
        sunrise = v.findViewById(R.id.alba);
        sunset = v.findViewById(R.id.tramonto);
        pressure = v.findViewById(R.id.pressione);
        update = v.findViewById(R.id.ultimoAgg);
        return v;
    }

    public void execute(String cap){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("cap", cap);
        editor.apply();
        new Function.placeIdTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,cap,"it");
    }

    @Override
    public void processFinish(String sCity, String temperature, String wTempMin, String wTempMax, String humidity, String wPressure, String wUpdate, String iconText, String wSunrise, String wSunset) {
        city.setText(sCity);
        temp.setText(temperature);
        hum.setText(c.getString(R.string.weather_humidity) + humidity);
        weatherIcon.setText(Html.fromHtml(iconText));
        tempMin.setText(c.getString(R.string.weather_temperature_min) + wTempMin);
        tempMax.setText(c.getString(R.string.weather_temperature_max) + wTempMax);
        pressure.setText(c.getString(R.string.weather_pressure) + wPressure);
        sunset.setText(c.getString(R.string.weather_sunset) + wSunset);
        sunrise.setText(c.getString(R.string.weather_sunrise) + wSunrise);
        update.setText(c.getString(R.string.weather_update_on) + wUpdate);
    }
}

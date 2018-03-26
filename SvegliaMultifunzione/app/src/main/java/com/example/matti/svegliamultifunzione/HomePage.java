package com.example.matti.svegliamultifunzione;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.weather.Function;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.things.userdriver.location.GpsDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import notificationobject.NotificationObject;

public class HomePage extends Activity implements Observer {

    private static Observer p;
    ListView notificationList;
    GridView pApplication;
    static ArrayList<NotificationObject> list;
    static Adapter adapter;
    static AdapterApp adapterApp;
    NotificationService s;
    TextView city, weatherIcon, temp, hum, tempMin,tempMax;
    static Context c;
    FusedLocationProviderClient location;
    static List<ApplicationInfo> appList;
    int flags = PackageManager.GET_META_DATA |
            PackageManager.GET_SHARED_LIBRARY_FILES |
            PackageManager.GET_UNINSTALLED_PACKAGES;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        list = new ArrayList<>();
        s = new NotificationService();
        s.addObserver(this);
        c = this;
        city = findViewById(R.id.city);
        weatherIcon = findViewById(R.id.weatherIcon);
        weatherIcon.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf"));
        temp = findViewById(R.id.temperature);
        hum = findViewById(R.id.humidity);
        tempMin = findViewById(R.id.tempMin);
        btn=findViewById(R.id.btn);
        tempMax = findViewById(R.id.tempMax);
        location = LocationServices.getFusedLocationProviderClient(this);
        notificationList = findViewById(R.id.notificationList);
        pApplication = findViewById(R.id.applicationList);
        adapter = new Adapter(getApplicationContext(), R.layout.activity_home_page,list);

        notificationList.setAdapter(adapter);

        p = this;

        //Toast.makeText(c, ""+appList.size(), Toast.LENGTH_SHORT).show();
        List<ApplicationInfo> tempA=getPackageManager().getInstalledApplications(flags);
        appList= getPackageManager().getInstalledApplications(flags);
        for(ApplicationInfo app : tempA) {
            if((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                appList.remove(app);
                Log.d("SYSTEM","REMOVEDD");
            }else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            {
                appList.remove(app);
                Log.d("SYSTEM","REMOVEDD");
            }
        }
        Toast.makeText(c, "number "+appList.size(), Toast.LENGTH_SHORT).show();
        adapterApp = new AdapterApp(getApplicationContext(), R.layout.activity_home_page,getPackageManager(),appList);

        /*final ArrayAdapter<ApplicationInfo> gridViewArrayAdapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1, getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA));*/

        // Data bind GridView with ArrayAdapter (String Array elements)
        pApplication.setAdapter(adapterApp);
         adapterApp.addAll(appList);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        final Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String sCity, String description, String temperature, String humidity, String weather_pressure, String pdatedOn, String iconText, String sun_rise, String tempMini,String tempMaxi) {
                city.setText(sCity);
                temp.setText(temperature);
                hum.setText("Humidity: " + humidity);
                weatherIcon.setText(Html.fromHtml(iconText));
                tempMin.setText(tempMini);
                tempMax.setText(tempMaxi);

            }
        });
        location.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Toast.makeText(HomePage.this, "getted", Toast.LENGTH_SHORT).show();
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Toast.makeText(HomePage.this, "ok", Toast.LENGTH_SHORT).show();
                            asyncTask.execute(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                        }else{
                            Toast.makeText(HomePage.this, "errore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        pApplication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HomePage.this, appList.get(i).packageName, Toast.LENGTH_SHORT).show();
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appList.get(i).packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

    }

    @Override
    public void update(Observable observable, final Object o) {
        list.add((NotificationObject) o);
        new Update().execute();
    }

    class Update extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            });
            return null;
        }
    }
}
package com.example.matti.svegliamultifunzione;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.matti.svegliamultifunzione.news.NewsFrag;
import com.example.matti.svegliamultifunzione.weather.SetCity;
import com.example.matti.svegliamultifunzione.weather.WeatherFrag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import notificationobject.NotificationObject;

public class HomePage extends FragmentActivity {

    private static ConstraintLayout lay;

    //private ListView notificationList;
    public static boolean isSplashRunning;
    static ArrayList<NotificationObject> list;
    private static LinkedHashMap<String,List<NotificationObject>> hList;
    //static Adapter adapter;
    private static ProgressBar discBlBar;
    private static ImageView discBlIm;
    static Context c;
    private SharedPreferences pref;
    private WeatherFrag weather;
    private NewsFrag news;
    private NotMessageAdapter adapterSingleMessage;
    private static RecyclerView notificationList;
    private static RecViewNotAdapter adapter;
    static List<String> packRec;
    private Handler handler = new Handler();
    public static Handler notificationComing;
    private Runnable update = new Runnable() {

        @Override
        public void run() {
            news = new NewsFrag();
            weather = new WeatherFrag();
            handler.postDelayed(update, 21600000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pref = getPreferences(Context.MODE_PRIVATE);
        c = this;
        packRec=new ArrayList<>();
        discBlBar = findViewById(R.id.disconnectedBar);
        discBlIm = findViewById(R.id.imageBlDisc);
        lay = findViewById(R.id.laysnack);
        isSplashRunning = false;
        news = new NewsFrag();
        weather = new WeatherFrag();
        hList=new LinkedHashMap<>();
        saveCap(getIntent().getExtras().getString("cap"));
        list = new ArrayList<>();
        notificationComing = new Handler();
        new NotificationService(notificationComing);
        notificationList = findViewById(R.id.notification_list);
        //adapter = new Adapter(getApplicationContext(), R.layout.activity_home_page, list);
        adapter=new RecViewNotAdapter(this,hList);
        notificationList.setLayoutManager(new LinearLayoutManager(this));
        notificationList.setAdapter(adapter);
        TabHost host = findViewById(R.id.tabs);

        host.setup();
        //Tab 1
        host.addTab(host.newTabSpec(getString(R.string.weather)).setContent(R.id.weather).setIndicator(getString(R.string.weather)));
        //Tab 2
        host.addTab(host.newTabSpec(getString(R.string.news)).setContent(R.id.news).setIndicator(getString(R.string.news)));

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Toast.makeText(HomePage.this, s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equals(getString(R.string.news))) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.news, news).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.weather, weather).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(new BluetoothListener(), filter);
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
        Log.d("VOLUME MAX",am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)+"");
    }

    private void saveCap(String cap) {
        if (!cap.isEmpty()) {
            Toast.makeText(this, cap, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("cap", cap);
            editor.apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.weather, weather).commit();
        } else {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.weather, new SetCity()).commit();
        }
        ImageView internet= findViewById(R.id.open_internet);
        internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,WebViewAvtivity.class));
            }
        });
    }

    public static void disconnected(boolean isDisconnected) {
        if (isDisconnected) {
            discBlIm.setVisibility(View.VISIBLE);
            discBlBar.setVisibility(View.VISIBLE);
            ((AnimationDrawable) discBlIm.getBackground()).start();
        } else {
            discBlIm.setVisibility(View.INVISIBLE);
            discBlBar.setVisibility(View.INVISIBLE);
            ((AnimationDrawable) discBlIm.getBackground()).stop();
        }
    }

    public static Handler getHandler(){
        notificationComing = new Handler();
        return notificationComing;
    }
    public static void handle(NotificationObject o) {
        if (o != null) {
            try {
                Palette p = Palette.from(BitmapFactory.decodeByteArray(o.getIcon(), 0, o.getIcon().length)).generate();
                o.setAppColor(p.getDominantSwatch().getRgb());
                Log.d("COLOR", "OK");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        /*if(!isSplashRunning)
            new NotifyComingNot(c).execute(o.getAppColor());
        isSplashRunning=true;*/
        /*Snackbar.make(lay, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        //snackbar.se*/
            if (o.getId() != 0) {
                if(!hList.containsKey(o.getPack())) {
                    ArrayList<NotificationObject> temp= new ArrayList<>();
                    temp.add(o);
                    hList.put(o.getPack(),temp);
                    //packRec.add(o.getPack());
                    //list.add(o);
                    adapter.notifyDataSetChanged();
                }else{
                    List<NotificationObject> temp= hList.get(o.getPack());
                    temp.add(o);
                    hList.put(o.getPack(),temp);
                    adapter.notifyDataSetChanged();
                }
                for (int i = 0; i < list.size(); i++) {
                    Log.d("Notification list id", list.get(i).getId() + "");
                }
                //adapter.clear();
                //adapter.addAll(list);


            }
        }
    }
}
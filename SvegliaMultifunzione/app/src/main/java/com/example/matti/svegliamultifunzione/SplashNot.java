package com.example.matti.svegliamultifunzione;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

public class SplashNot extends Activity {

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_not);
        ConstraintLayout lay=findViewById(R.id.splash);
        Log.d("COLORRRR","c "+getIntent().getExtras().getInt("color"));
        lay.setBackgroundColor(getIntent().getIntExtra("color",0));
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

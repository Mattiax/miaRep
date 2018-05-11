package com.example.matti.svegliamultifunzione;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import notificationobject.NotificationObject;

public class NotifyComingNot extends AsyncTask<Object,Void,Void> {

    Context c;

    NotifyComingNot(Context c){
        this.c=c;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        Intent i= new Intent(c,SplashNot.class);
        int z=(int)objects[0];
        Log.d("COlorrrrr asyn",""+z);
        i.putExtra("color",z);
        c.startActivity(i);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HomePage.isSplashRunning=false;
    }
}

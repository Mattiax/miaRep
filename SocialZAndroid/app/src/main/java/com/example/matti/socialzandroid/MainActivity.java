package com.example.matti.socialzandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DoPOST a= new DoPOST();
        try {
            Toast.makeText(this, a.execute().get(), Toast.LENGTH_SHORT).show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}

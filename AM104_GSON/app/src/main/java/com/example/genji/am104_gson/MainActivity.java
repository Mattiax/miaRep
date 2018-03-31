package com.example.genji.am104_gson;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    private RFService mService;
    private ExpandableListView expList;
    private AdapterList adapter;
    private ImageView image;
    private ProgressBar waiting;
    private ArrayList<Round> rounds = new ArrayList<>();
    String squadraCuore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "call RFInterface.getEmbeddedService()");
        mService = RFService.retrofit.create(RFService.class);
        expList = findViewById(R.id.simpleList);
        TextView tv = new TextView(this);
        tv.setText(getResources().getString(R.string.header));
        waiting = findViewById(R.id.waitingResponse);
        image = findViewById(R.id.ball);
        squadraCuore="";
        image.setVisibility(View.INVISIBLE);
        final EditText input= findViewById(R.id.editText);
        Button ok=findViewById(R.id.button);
        GetResponse a = new GetResponse();
        try {
            a.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().toString().length()>0){
                    squadraCuore=input.getText().toString().trim().toLowerCase();
                    if(!rounds.isEmpty()){
                        adapter= new AdapterList(MainActivity.this, rounds,squadraCuore);
                        expList.setAdapter(adapter);
                    }
                }
            }
        });
    }

    class GetResponse extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getRFResponse();
            return null;
        }
    }

    public void getRFResponse() {
        mService.getPojo().enqueue(new Callback<Pojo>() {


            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {

                Log.d("MainActivity", "onresponse()");

                if (response.isSuccessful()) {
                    Log.d("MainActivity", "onresponse.isSuccessful()");
                    Toast.makeText(MainActivity.this, "get response", Toast.LENGTH_SHORT);
                    rounds = (ArrayList<Round>) response.body().getRounds();
                    adapter = new AdapterList(MainActivity.this, rounds,squadraCuore);
                    expList.setAdapter(adapter);
                    waiting.setVisibility(View.INVISIBLE);
                    Animation animation1 =
                            AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    image.startAnimation(animation1);
                    image.setVisibility(View.VISIBLE);

                } else {
                    int statusCode = response.code();
                    Toast.makeText(MainActivity.this, getString(R.string.error)+statusCode, Toast.LENGTH_LONG).show();
                    Log.d("MainActivity", "handle request errors");
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    void showErrorMessage() {
        Log.d("MainActivity", "Error");
    }
}

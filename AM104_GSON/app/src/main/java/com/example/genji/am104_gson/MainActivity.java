package com.example.genji.am104_gson;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    private RFService mService;
    ListView slist;
    Adapter ad;
    ArrayAdapter<Round> ada;
    private ArrayList<Round> rounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "call RFInterface.getEmbeddedService()");
        mService = RFService.retrofit.create(RFService.class);
        slist = findViewById(R.id.simpleList);
        TextView tv = new TextView(this);
        tv.setText(getResources().getString(R.string.header));

        slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, rounds.get(i).getMatches().get(0).getTeam1().getName(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Partite")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

            }
        });

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener((View view) -> {
            Log.d("MainActivity", "getRFResponse()");
            getRFResponse();
        });
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
                    ada = new ArrayAdapter<>(MainActivity.this, android.R.layout.preference_category, rounds);
                    ad = new Adapter(getApplicationContext(), R.layout.activity_main, rounds);
                    slist.setAdapter(ad);

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Log.d("MainActivity", "handle request errors");
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    /*void showRounds(ArrayList<Round> rounds){

        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.setMargins(40, 10, 10, 10);
        for(final Round round : rounds){
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(round.getName());
            tv.setOnClickListener((View view) ->
                Toast.makeText(MainActivity.this, round.getName(), Toast.LENGTH_SHORT).show()
            );
            linearLayout.addView(tv);
        }
        scrollView.addView(linearLayout);
    }*/

    void showErrorMessage() {
        Log.d("MainActivity", "Error");
    }
}

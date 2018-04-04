package com.example.matti.socialzandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner dropDown;
    static ExpandableListView listView;
    static ListAdapter expandableListAdapter;
    ArrayAdapter<String> adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dropDown= findViewById(R.id.hobbies);
        listView=findViewById(R.id.list);

        new GetHobbies().execute();
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new DoPOST(MainActivity.this).execute(list.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class GetHobbies extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                list = new ArrayList<>();
                url = new URL("http://192.168.1.2:8080/SocialZ/getHobbiesService");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("hobby", "");

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());

                InputStream in = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                String result=rd.readLine();
                result=result.substring(1,result.length()-1).replaceAll("\"","");
                list = Arrays.asList(result.split("\\s*,\\s*"));
                list.add("Tutti");
                rd.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,list);
            dropDown.setAdapter(adapter);
        }
    }

}

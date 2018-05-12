package com.example.matti.socialzandroid;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Fragment {

    static Spinner dropDown;
    static ExpandableListView listView;
    static ListAdapter expandableListAdapter;
    static ArrayAdapter<String> adapter;
    static List<String> list;
    static Context c;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("frag","main");
        View view = inflater.inflate(R.layout.activity_main, container, false);
        dropDown = view.findViewById(R.id.hobbies);
        listView = view.findViewById(R.id.list);
        c=getActivity().getApplicationContext();
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new DoPOST(c).execute(list.get(i));
                Log.d("hobby",list.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new GetHobbies().execute();

        return view;
    }

    static void setResult(String ris){
        list.addAll(Arrays.asList(ris.split("\\,*,\\,*")));
        list.add("Tutti");
        adapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, list);
        dropDown.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
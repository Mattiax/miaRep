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
        dropDown = findViewById(R.id.hobbies);
        listView = findViewById(R.id.list);

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

        private final String NAMESPACE = "http://192.168.1.2:8080/ProvaWebServices/Maillist";
        private final String URL = "http://192.168.1.2:8080/ProvaWebServices/Maillist";
        private final String SOAP_ACTION = "";//errore
        private final String METHOD_NAME = "getHobbies";

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                list = new ArrayList<>();
                url = new URL("http://192.168.1.2:8080/ProvaWebServices/Maillist");
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                //Create envelope
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                try {
                    //Invole web service
                    androidHttpTransport.call(SOAP_ACTION, envelope);
                    //Get the response
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    //Assign it to fahren static variable
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
            dropDown.setAdapter(adapter);
        }
    }

    /*private class CallSOAP extends AsyncTask<String, Void, String> {

        String NAMESPACE = "hobbyMailList";
        String METHOD_NAME = "getHobbiesWeb";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        String URL = "http://192.168.1.2:8080/SocialZ/hobbyMailList";

        @Override
        protected String doInBackground(String... params) {

            SoapObject so = new SoapObject(NAMESPACE, METHOD_NAME);//name space nome web service //name webmethod
            so.addProperty("hobby", "pallavolo");
            SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            sse.setOutputSoapObject(so);
            HttpTransportSE hse = new HttpTransportSE(URL);

            try {
                hse.call("/getHobbiesWeb", sse);//action linsieme di namespace e name
                SoapPrimitive primitive = (SoapPrimitive) sse.getResponse();
                Toast.makeText(getApplicationContext(), primitive.toString(), Toast.LENGTH_SHORT);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
}
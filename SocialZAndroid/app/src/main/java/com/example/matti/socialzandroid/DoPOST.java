package com.example.matti.socialzandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

class DoPOST extends AsyncTask {

    private Context c;
    private final String NAMESPACE = "http://web.pkg/";
    private final String URL = "http://37.117.166.81:8080/WebServices/SocialZ?wsdl";
    private final String METHOD_NAME = "getMailList";
    private final String SOAP_ACTION = "";
    private String hobby;

    DoPOST(Context c){
        hobby=null;
        this.c=c;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        String hobby=String.valueOf(objects[0]);
        this.hobby=hobby.equals("Tutti")?null:hobby;
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("hobby",this.hobby);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                return response.getProperty(0).toString();
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
        String s=(String)o;
        MailList ml=new MailList(s);
        if(ml.getMailList()!=null) {
            MainActivity.expandableListAdapter = new ListAdapter(c, ml.getMailList(),hobby);
            MainActivity.listView.setAdapter(MainActivity.expandableListAdapter);
        }
    }
}

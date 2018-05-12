package com.example.matti.socialzandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;

public class GetHobbies extends AsyncTask {

    private final String NAMESPACE = "http://web.pkg/";
    private final String URL = "http://37.117.166.81:8080/WebServices/SocialZ?wsdl";
    private final String METHOD_NAME = "getHobbies";
    private final String SOAP_ACTION = "";


    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            MainActivity.list = new ArrayList<>();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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
        if(o!=null) {
            MainActivity.setResult((String) o);
        }
    }
}

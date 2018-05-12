package com.example.matti.socialzandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class DoSignIn extends AsyncTask<String,Integer,Integer> {

    private String json;

    public DoSignIn(String json,Context c){
        this.json=json;
    }

    private final String NAMESPACE = "http://web.pkg/";
    private final String URL = "http://37.117.166.81:8080/WebServices/SocialZ?wsdl";
    private final String METHOD_NAME = "registrazione";
    private final String SOAP_ACTION = "";

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("dati",json);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                return Integer.valueOf(response.getProperty(0).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer rs) {
        super.onPostExecute(rs);
        Registrazione.showResponse(rs);
    }
}

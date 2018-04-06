package com.example.matti.socialzandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

class DoPOST extends AsyncTask {

    private Context c;

    DoPOST(Context c){
        this.c=c;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        String hobby=String.valueOf(objects[0]);
        try {
            URL url = new URL("http://192.168.1.2:8080/SocialZ/mailList");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("hobby", hobby);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            InputStream in=conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));

            String result=rd.readLine();
            Log.d("ris",result);
            if(result.length()<3){
                MailListHobby mo = new MailListHobby(null, hobby);
                List<MailListHobby> ret = new ArrayList<>();
                ret.add(mo);
                return ret;
            }else {
                List<MailListHobby> ret= new ArrayList<>();
                if(result.charAt(0) == '%') {
                    Log.d("Ciao", "ad");
                    MailListHobby mail;
                    String temp= result.substring(1);

                    try {
                        for (int i = 0; i < result.length() / 2; i++) {
                            mail = new MailListHobby();
                            String titolo = temp.substring(0, temp.indexOf(","));
                            String emails = temp.substring(temp.indexOf(",")+1, temp.indexOf("%"));
                            temp = temp.substring(temp.indexOf("%") + 1);
                            mail.setHobby(titolo);
                            mail.setMail(Arrays.asList(emails.split("\\,*,\\,*")));
                            ret.add(mail);
                        }
                    }catch(Exception e){

                    }
                }else {
                    result = result.substring(1, result.length() - 1);//.replaceAll("\"","");
                    List<String> ris = Arrays.asList(result.split("\\s*,\\s*"));
                    Log.d("list ris", "" + ris.size());
                    rd.close();
                    conn.disconnect();
                    MailListHobby mo = new MailListHobby(ris, hobby);
                    ret.add(mo);
                }
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object s) {
        super.onPostExecute(s);
        List<MailListHobby> ris=(List<MailListHobby>) s;
        if(ris!=null) {
            MainActivity.expandableListAdapter = new ListAdapter(c, ris);
            Log.d("List", "" + ((List<MailListHobby>) s).size());
            MainActivity.listView.setAdapter(MainActivity.expandableListAdapter);
        }
    }
}

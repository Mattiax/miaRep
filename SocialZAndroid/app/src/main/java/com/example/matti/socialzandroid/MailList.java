package com.example.matti.socialzandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MailList {

    LinkedHashMap<String,List<String>> mailList;

    public MailList(String ris) {
        mailList=new LinkedHashMap<>();
        try {
            JSONObject ob = new JSONObject(ris);
            if(ob.length()>0) {
                JSONArray jsHobbies = ob.getJSONArray("hobbies");
                int size = jsHobbies.length();
                for (int i = 0; i < size; i++) {
                    try {
                        JSONArray temp;
                        temp = ob.getJSONArray(jsHobbies.getString(i));
                        List<String> mail = new ArrayList<>();
                        for (int j = 0; j < temp.length(); j++) {
                            mail.add(temp.getString(j));
                        }
                        mailList.put(jsHobbies.getString(i), mail);
                    } catch (JSONException value) {
                    }
                }
            }
        }catch(JSONException e){e.printStackTrace();}
    }

    public LinkedHashMap<String, List<String>> getMailList() {
        return mailList;
    }
}

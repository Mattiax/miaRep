/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mattia.musone
 */
public class MailList {
	
	 LinkedHashMap<String,List<String>> mailList;
	 
    public MailList(String ris, List<String> hobbies,String hobby) {
        mailList=new LinkedHashMap<>();
        int size=hobby==null?hobbies.size():1;
        try {
            JSONObject ob = new JSONObject(ris);
            for (int i = 0; i < size; i++) {
                try {
                    JSONArray temp;
                    if (size == 1) {
                        temp = ob.getJSONArray(hobby);
                    } else {
                        temp = ob.getJSONArray(hobbies.get(i));
                    }
                    List<String> mail = new ArrayList<>();
                    for (int j = 0; j < temp.length(); j++) {
                        mail.add(temp.getString(j));
                    }
                    mailList.put(hobbies.get(i), mail);
                }catch(JSONException value){}
            }
        }catch(JSONException e){e.printStackTrace();}
    }
	
	public MailList(){}

    public LinkedHashMap<String, List<String>> getMailList() {
        return mailList;
    }

	public void setMailList(LinkedHashMap<String, List<String>> mailList) {
		this.mailList = mailList;
	}
	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author MATTI
 */
public class MailList implements Serializable {

    private JSONObject ris;

    public MailList() {

    }

    public MailList(List<String[]> lista) {
        ris = new JSONObject();
        try {
            JSONArray a = new JSONArray();
            String hobby = lista.get(0)[0];
            for (int i = 0; i < lista.size(); i++) {
                if (!hobby.equals(lista.get(i)[0])) {
                    ris.put(hobby, a);
                    a = new JSONArray();
                    hobby = lista.get(i)[0];
                }
                a.put(lista.get(i)[1]);
            }
            ris.put(hobby, a);
        } catch (Exception e) {
        }
    }

    public JSONObject getJSON() {
        return ris;
    }
}

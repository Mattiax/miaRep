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

/**
 *
 * @author MATTI
 */
public class MailList implements Serializable{

    HashMap<String, List<String>> list;

    public MailList() {
        
    }
    
    public MailList(List<String[]> lista) {
        if (!lista.isEmpty()) {
            list = new HashMap<>();
            String hobby = lista.get(0)[0];
            List<String> lisTemp = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                lisTemp.add(lista.get(i)[1]);
                if (!lista.get(i)[0].equals(hobby)) {
                    list.put(hobby, lisTemp);
                    lisTemp = new ArrayList<>();
                    hobby = lista.get(i)[0];
                }
            }
        }
    }

    public HashMap<String, List<String>> getList() {
        return list;
    }

    public void setList(HashMap<String, List<String>> list) {
        this.list = list;
    }
    
}

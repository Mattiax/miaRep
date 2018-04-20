/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.web;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import pkg.db.DBHelper;

/**
 *
 * @author mattia.musone
 */
@WebService(serviceName = "Maillist")
public class Maillist {

    /**
     * This is a sample web service operation
     *
     * @param hobby
     * @return
     */
    @WebMethod(operationName = "getMailList")
    public String getMailList(@WebParam(name = "hobby") String hobby) {
        String ris = DBHelper.getAllEmailsHobby(hobby);
        System.out.println(ris);
        return ris;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getHobbies")
    public String getHobbies() {
        return DBHelper.getHobbies();
    }
}

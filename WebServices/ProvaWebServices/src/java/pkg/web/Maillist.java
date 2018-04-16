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
	 */
	@WebMethod(operationName = "hello")
	public String hello(@WebParam(name = "name") String txt) {
		String ris=DBHelper.getAllEmailsHobby();
		System.out.println(ris);
		return ris;
	}
}

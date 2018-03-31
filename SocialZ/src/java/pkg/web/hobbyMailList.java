/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.web;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author MATTI
 */
@WebService(serviceName = "hobbyMailList")
public class hobbyMailList {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "list")
    public List<String> list(@WebParam(name = "hobby") String hobby) {
        //TODO write your implementation code here:
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public String list(@WebParam(name = "hobby") String hobby) throws Exception {
        String json = "{\"hobby\":\"" + hobby + "\"}";
        final String urls = "http://localhost:8080/SocialZ/mailList";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(json.getBytes().length));
            connection.setDoOutput(true);
            ObjectOutputStream wr = new ObjectOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(json);
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

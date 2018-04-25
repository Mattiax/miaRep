/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pkg.oggetti.Risposta;

/**
 *
 * @author mattia.musone
 */
public class DBHelper {

    private static Connection conn;

    private static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:D:\\Windows\\Desktop\\miaRep\\SocialZ\\Database.db");
            } catch (ClassNotFoundException | SQLException ex) {
            }
        }
        return conn;
    }

    public static String getHobbies() {
        String sql = "SELECT * "
                + "FROM HOBBY;";
        String ris = "";
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ris += rs.getString("hobby") + ",";
            }
            ris = ris.substring(0, ris.length() - 1);
        } catch (SQLException e) {

        }
        return ris;
    }

    public static int registrati(String json) {
        
        String sql = "INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            System.out.println(json);
            JSONObject data=new JSONObject(json);
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1,data.getString("email"));
            st.setString(2,data.getString("password"));
            st.setString(3,data.getString("nome"));
            st.setString(4,data.getString("cognome"));
            st.setString(5,data.getString("indirizzo").isEmpty()?null:data.getString("indirizzo"));
            st.setString(6,data.getString("sesso"));
            st.setString(7,data.getString("dataNascita"));
            st.setString(8,null);
            st.setString(9,data.getString("telefono").isEmpty()?null:data.getString("telefono"));
            st.setInt(10,data.getInt("privacy"));
            st.executeUpdate();
        }catch(JSONException js){
             js.printStackTrace();
            return 3;
        }
        catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("UNIQUE")) {
                return Risposta.UNIQUE_FAIL;
            } else {
                return Risposta.GENERAL_ERROR;
            }
        }
        return Risposta.OK;
    }

    public static String getAllEmailsHobby(String hobby) {
        String sql = "";
        PreparedStatement st = null;
        JSONObject ris = new JSONObject();
        try {
            if (hobby.isEmpty()) {
                sql += "SELECT hobby,HOBBYPRATICATO.email "
                        + "FROM HOBBYPRATICATO,PERSONA "
                        + "WHERE HOBBYPRATICATO.email=PERSONA.email AND permesso = 1 "
                        + "ORDER BY hobby;";
                st = getConnection().prepareStatement(sql);
            } else {
                sql += "SELECT * "
                        + "FROM HOBBYPRATICATO,PERSONA "
                        + "WHERE hobby = ? AND HOBBYPRATICATO.email=PERSONA.email AND permesso = 1;";
                st = getConnection().prepareStatement(sql);
                st.setString(1, hobby);
            }
            System.out.println(sql + hobby);
            ResultSet rs = st.executeQuery();
            if(rs.isClosed()){
                return "{}";
            }
            ris = new JSONObject();
            JSONArray temp = new JSONArray();
            JSONArray hobbies = new JSONArray();
            String hobbyTemp = rs.getString("hobby");
            hobbies.put(hobbyTemp);
            while (rs.next()) {
                if (!hobbyTemp.equals(rs.getString("hobby"))) {
                    ris.put(hobbyTemp, temp);
                    temp = new JSONArray();
                    hobbyTemp = rs.getString("hobby");
                    hobbies.put(hobbyTemp);
                }
                temp.put(rs.getString("email"));
            }
            ris.put(hobbyTemp, temp);
            ris.put("hobbies",hobbies);
            rs.close();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println(ris.toString());
        return ris.toString();
    }

    public static String getPersona(String email) {
        String sql = "SELECT * FROM PERSONA "
                + "WHERE email = ?;";
        JSONObject ris = new JSONObject();
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            boolean isVisible = false;
            if (rs.next()) {
                if (rs.getBoolean("permesso")) {
                    isVisible = true;
                    ris.put("email", rs.getString("email"));
                    ris.put("password", rs.getString("password"));
                    ris.put("nome", rs.getString("nome"));
                    ris.put("cognome", rs.getString("cognome"));
                    ris.put("indirizzo", rs.getString("indirizzo"));
                    ris.put("sesso", rs.getString("sesso"));
                    ris.put("dataNascita", rs.getString("dataNascita"));
                    ris.put("immagine", rs.getString("foto"));
                    ris.put("telefono", rs.getString("telefono"));
                    ris.put("visibile", true);
                } else {
                    isVisible = false;
                    ris.put("visibile", false);
                    ris.put("email", rs.getString("email"));
                }
            }
            JSONArray hobbies = new JSONArray();
            if (isVisible) {
                sql = "SELECT hobby FROM HOBBYPRATICATO "
                        + "WHERE email = ?;";
                st = getConnection().prepareStatement(sql);
                st.setString(1, email);
                rs = st.executeQuery();
                while (rs.next()) {
                    hobbies.put(rs.getString("hobby"));
                }
            }
            ris.put("hobbies", hobbies);
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
            return "";
        }
        return ris.toString();
    }
}

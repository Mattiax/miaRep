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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static String getAllEmailsHobby(String hobby) {
        String sql = "SELECT hobby,HOBBYPRATICATO.email "
                + "FROM HOBBYPRATICATO,PERSONA "
                + "WHERE ";
        if (!hobby.isEmpty()) {
            sql += "HOBBYPRATICATO.hobby = ? AND ";
        } else {
            sql += "HOBBYPRATICATO.email=PERSONA.email AND permesso = 1 "
                    + "ORDER BY hobby;";
        }
        JSONObject ris = null;
        try {
            PreparedStatement st = getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ris = new JSONObject();
            JSONArray temp = new JSONArray();
            String hobbyTemp = rs.getString("hobby");
            while (rs.next()) {
                if (!hobbyTemp.equals(rs.getString("hobby"))) {
                    ris.put(hobbyTemp, temp);
                    temp = new JSONArray();
                    hobbyTemp = rs.getString("hobby");
                }
                temp.put(rs.getString("email"));
            }
            ris.put(hobbyTemp, temp);
        } catch (SQLException | JSONException e) {
        }
        return ris.toString();
    }
}

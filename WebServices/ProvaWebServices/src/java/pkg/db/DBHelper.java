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
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pkg.oggetti.MailList;

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
				conn = DriverManager.getConnection("jdbc:sqlite:Z:\\MusoneSocial\\SocialZ\\Database.db");
			} catch (ClassNotFoundException | SQLException ex) {
			}
		}
		return conn;
	}

	public static String getAllEmailsHobby() {
		String sql = "SELECT hobby,HOBBYPRATICATO.email "
				+ "FROM HOBBYPRATICATO,PERSONA "
				+ "WHERE HOBBYPRATICATO.email=PERSONA.email AND permesso = 1 "
				+ "ORDER BY hobby;";
		JSONObject ris = null;
		try {
			PreparedStatement st = getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			ris = new JSONObject();
			JSONArray temp = new JSONArray();
			String hobby = rs.getString("hobby");
			while (rs.next()) {
				if (!hobby.equals(rs.getString("hobby"))) {
					ris.put(hobby, temp);
					temp = new JSONArray();
					hobby = rs.getString("hobby");
				}
				temp.put(rs.getString("email"));
			}
			ris.put(hobby, temp);
		} catch (SQLException | JSONException e) {}
		return ris.toString();
	}
}

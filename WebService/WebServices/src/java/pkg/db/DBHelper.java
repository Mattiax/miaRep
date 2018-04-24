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

	public static int registrati() {
		String sql = "INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?);";
		try {
			PreparedStatement st = getConnection().prepareStatement(sql);
			st.execute();
		} catch (SQLException e) {
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
						+ "FROM HOBBYPRATICATO "
						+ "WHERE hobby = ?;";
				st = getConnection().prepareStatement(sql);
				st.setString(1, hobby);
			}
			System.out.println(sql + hobby);
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
			e.printStackTrace();
		}
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
				if (rs.getBoolean("acc")) {
					isVisible = true;
					ris.put("email", rs.getString(""));
					ris.put("password", rs.getString(""));
					ris.put("nome", rs.getString(""));
					ris.put("cognome", rs.getString(""));
					ris.put("indirizzo", rs.getString(""));
					ris.put("sesso", rs.getString(""));
					ris.put("dataNascita", rs.getString(""));
					ris.put("immagine", rs.getString(""));
					ris.put("telefono", rs.getString(""));
					ris.put("visibile", rs.getBoolean(""));
				} else {
					isVisible = false;
					ris.put("visibile", "flase");
					ris.put("email", rs.getString(""));
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

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
import pkg.oggetti.MailList;
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
		String ris = "";
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
		LinkedHashMap<String, List<String>> ris1 = new LinkedHashMap<>();

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

			/*List<String> temp = new ArrayList<>();
			String hobbyTemp = rs.getString("hobby");
			while (rs.next()) {
				if (!hobbyTemp.equals(rs.getString("hobby"))) {
					ris1.put(hobbyTemp, temp);
					temp = new ArrayList<>();
					hobbyTemp = rs.getString("hobby");
				}
				temp.add(rs.getString("email"));
			}
			ris1.put(hobbyTemp, temp);
			MailList ml= new MailList();
			ml.setMailList(ris1);*/
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
}

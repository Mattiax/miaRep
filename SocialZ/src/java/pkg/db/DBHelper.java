/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import pkg.oggetti.Utente;

/**
 *
 * @author mattia.musone
 */
public class DBHelper {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void sigIn(Utente u) {
		String sql = "INSERT () "
				+ " VALUES (?,?,?,?,?,?,?,?);";
		jdbcTemplate.update(sql, u.getNome(), u.getCognome(), u.getDataNascita(), u.getSesso(), u.getTelefono(), u.getEmail(), u.getPassword(), u.getHobbies());
	}

	public Utente getUser() {
		String sql = "INSERT () "
				+ " VALUES (?,?,?,?,?,?,?,?);";
		Utente ris = jdbcTemplate.query(sql, (ResultSet rs) -> rs.next()
				? new Utente(
						rs.getString("Nome"),
						rs.getString("Cognome"),
						rs.getString("Sesso").charAt(0),
						rs.getString("Telefono"),
						rs.getString("Nome"),
						rs.getString("Nome"),
						rs.getString("Nome"),
						null) : null);
		return ris;
	}
}

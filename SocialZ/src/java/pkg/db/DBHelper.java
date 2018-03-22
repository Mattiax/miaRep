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
import pkg.oggetti.Messaggio;
import pkg.oggetti.Utente;

/**
 *
 * @author mattia.musone
 */
public class DBHelper implements DB {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public DBHelper() {

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void sigIn(Utente u) {
        String sql = "INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql, u.getEmail(), u.getPassword(), u.getNome(), u.getCognome(), u.getIndirizzo(), u.getSesso(), u.getDataNascita(), null, u.getTelefono(), u.getPermesso());
    }
    
    public void salvaMess(Messaggio m) {
        String sql = "INSERT INTO MESSAGGI(mittente,destinatario,messaggio,allegato,dataOra) "
                + " VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, m.getMittente(),m.getDestinatario(),m.getMessaggio(),null,m.getDataOra());
    }

    public Utente getUser(String email, String password) {
        System.out.println(email + "   " + password);
        String sql = "SELECT * FROM PERSONA WHERE email = '" + email + "' ";
        if(!password.isEmpty()){
            sql+="AND password = '" + password + "';";
        }
        Utente ris = jdbcTemplate.query(sql, (ResultSet rs) -> rs.next()
                ? new Utente(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("sesso").charAt(0),
                        rs.getString("dataNascita"),
                        null,
                        rs.getString("telefono"),
                        rs.getBoolean("permesso"),
                        null) : null);
        return ris;
    }

    public List<Utente> getAllUsers() {
        String sql = "SELECT * FROM PERSONA;";
        List<Utente> ris = jdbcTemplate.query(sql, new RowMapper<Utente>() {
            @Override
            public Utente mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Utente(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("sesso").charAt(0),
                        rs.getString("dataNascita"),
                        null,
                        rs.getString("telefono"),
                        rs.getBoolean("permesso"),
                        null);
            }
        });
        return ris;
    }

    public List<Messaggio> getConversazione(String mittente, String destinatario) {
        String sql = "SELECT * FROM MESSAGGI "
                + "WHERE (mittente = '" + mittente + "' AND destinatario ='" + destinatario + "') OR (destinatario = '" + mittente + "' AND mittente ='" + destinatario + "');";
        List<Messaggio> ris = jdbcTemplate.query(sql, new RowMapper<Messaggio>() {
            @Override
            public Messaggio mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Messaggio(
                        rs.getInt("id"),
                        rs.getString("mittente"),
                        rs.getString("destinatario"),
                        rs.getString("messaggio"),
                        rs.getString("dataOra"));
            }
        });
        return ris;
    }
}

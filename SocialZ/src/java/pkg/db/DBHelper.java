/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Richiesta;
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

    private void collegaHobby(String user, String[] hobby) {
        String sql = "INSERT INTO ELENCOHOBBIES(email,hobby) "
                + " VALUES (?,?);";
        for (int i = 0; i < hobby.length; i++) {
            jdbcTemplate.update(sql, user, hobby[i]);
        }
    }

    public void sigIn(Utente u) {
        String sql = "INSERT INTO PERSONA(email,password,nome,cognome,indirizzo,sesso,dataNascita,foto,telefono,permesso) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql, u.getEmail(), u.getPassword(), u.getNome(), u.getCognome(), u.getIndirizzo(), u.getSesso(), u.getDataNascita(), u.getImage(), u.getTelefono(), u.getPermesso());
        collegaHobby(u.getEmail(), u.getHobbies());
    }

    public long salvaMess(Messaggio m) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO MESSAGGI(mittente,destinatario,messaggio,allegato,dataOra) "
                + " VALUES (?,?,?,?,?);";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, m.getMittente());
                statement.setString(2, m.getDestinatario());
                statement.setString(3, m.getMessaggio());
                statement.setString(4, null);
                statement.setString(5, m.getDataOra());
                return statement;
            }
        }, holder);
        //jdbcTemplate.update(sql, m.getMittente(), m.getDestinatario(), m.getMessaggio(), null, m.getDataOra(), holder);
        return holder.getKey().longValue();
    }

    public void eliminaMess(int id) {
        String sql = "DELETE FROM MESSAGGI "
                + "WHERE id=?;";
        jdbcTemplate.update(sql, id);
    }

    public void eliminaMessGruppo(int id) {
        String sql = "DELETE FROM MESSAGGIGRUPPI "
                + "WHERE id=?;";
        jdbcTemplate.update(sql, id);
    }

    public List<String[]> getGruppi() {
        String sql = "SELECT * FROM GRUPPO;";
        List<String[]> ris = jdbcTemplate.query(sql, new RowMapper<String[]>() {
            @Override
            public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new String[]{rs.getString("nome"), rs.getString("descrizione")};
            }
        });
        return ris;
    }

    public Utente getUser(String email, String password) {
        System.out.println(email + "   " + password);
        String sql = "SELECT * FROM PERSONA WHERE email = '" + email + "' ";
        if (!password.isEmpty()) {
            sql += "AND password = '" + password + "';";
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

    public List<Utente> getAllUsers(String email) {
        String sql = "SELECT * FROM PERSONA WHERE "
                + "email <> '"+email+"';";
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

    public long aggiungiMessaggioGruppo(Messaggio m) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        System.out.println(m.getDestinatario());
        String sql = "INSERT INTO MESSAGGIGRUPPI(mittente,destinatario,messaggio,allegato,dataOra) "
                + " VALUES (?,?,?,?,?);";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, m.getMittente());
                statement.setString(2, m.getDestinatario());
                statement.setString(3, m.getMessaggio());
                statement.setString(4, null);
                statement.setString(5, m.getDataOra());
                return statement;
            }
        }, holder);
        return holder.getKey().longValue();
    }

    public int isPartecipante(String mittente, String destinatario) {
        System.out.println(mittente + destinatario);
        String sql = "SELECT email FROM PARTECIPANTI "
                + "WHERE email = ? AND nome = ? ;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, mittente, destinatario);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int hasRichiestaPartecipazione(String mittente, String destinatario) {
        System.out.println("richiesta");
        String sql = "SELECT idRichiesta FROM RICHIESTA "
                + "WHERE richiedente = ? AND destinatario = ? ;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, mittente, destinatario);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public List<Messaggio> getConversazioneGruppo(String mittente, String destinatario) {

        String sql = "SELECT id,mittente,destinatario,messaggio,dataOra,allegato "
                + "FROM MESSAGGIGRUPPI,PARTECIPANTI "
                + "WHERE email = '" + mittente + "' AND PARTECIPANTI.nome='" + destinatario + "' AND destinatario = '" + destinatario + "';";
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

    /*private String getAmministratore(String gruppo) {
        String sql = "SELECT email FROM AMMINISTRATORIGRUPPO "
                + "WHERE nomeGruppo = ? AND nome = ? ;";
        try{
         return jdbcTemplate.queryForObject(sql,Integer.class,mittente,destinatario);
        }catch(EmptyResultDataAccessException e){
            return-1;
        }
    }*/
    public void richiestaPartecipazioneGruppo(Messaggio m) {
        System.out.println(m.getDestinatario());
        String sql = "INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario) "
                + " VALUES(?, (SELECT email FROM AMMINISTRATORIGRUPPO WHERE nomeGruppo = ?),?,?);";
        jdbcTemplate.update(sql, m.getMessaggio(), m.getDestinatario(), m.getMittente(), m.getDestinatario());
    }

    public void creaGruppo(String amministratore, String nome, String descrizione, String[] partecipanti) {
        String sql = "INSERT INTO GRUPPO(nome,descrizione) "
                + " VALUES (?,?);";
        jdbcTemplate.update(sql, nome, descrizione);
        sql = "INSERT INTO PARTECIPANTI(nome,email) "
                + " VALUES (?,?);";
        for (int i = 0; i < partecipanti.length; i++) {
            jdbcTemplate.update(sql, nome, partecipanti[i]);
        }
        sql = "INSERT INTO AMMINISTRATORIGRUPPO(nomeGruppo,email) "
                + " VALUES (?,?);";
        jdbcTemplate.update(sql, nome, amministratore);
    }

    public List<String> getHobbies() {
        String sql = "SELECT * FROM HOBBIES;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("hobby");
            }
        });
        return ris;
    }
    
    public List<String> getHobbiesPersona(String utente) {
        String sql = "SELECT hobby FROM ELENCOHOBBIES "
                + "WHERE email = '"+utente+"';";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("hobby");
            }
        });
        return ris;
    }
    
    public List<Richiesta> getRichieste(String user) {
        String sql = "SELECT * FROM RICHIESTA "
                + "WHERE amministratore = '"+user+"';";
        List<Richiesta> ris = jdbcTemplate.query(sql, new RowMapper<Richiesta>() {
            @Override
            public Richiesta mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Richiesta(rs.getInt("idRichiesta"),rs.getString("richiedente"),rs.getString("destinatario"),rs.getString("descrizione"));
            }
        });
        return ris;
    }
    
    public void approvaRichiesta(int id, String richiedente,String gruppo){
        String sql = "DELETE FROM RICHIESTA "
                + "WHERE idRichiesta=?;";
        jdbcTemplate.update(sql, id);
        sql="INSERT INTO PARTECIPANTI (nome,email) VALUES(?,?)";
        jdbcTemplate.update(sql, gruppo, richiedente);
    }
    
    public List<String> getMailList(String hobby) {
        String sql = "SELECT ELENCOHOBBIES.email FROM ELENCOHOBBIES,PERSONA "
                + "WHERE hobby = '"+hobby+"' AND ELENCOHOBBIES.email=PERSONA.email AND permesso = 1;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("ELENCOHOBBIES.email");
            }
        });
        return ris;
    }
    
    public void nuovoHobby(String hobby){
        System.out.println("agg "+hobby);
        String sql="SELECT * FROM AMMINISTRATORISOCIAL;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("email");
            }
        });
        sql="INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario) "
                + "VALUES(?,?,?,?)";
        for(int i=0;i<ris.size();i++){
            System.out.println("aa");
            jdbcTemplate.update(sql, "Richiesta aggiunta hobby #%="+hobby,ris.get(i),null,null);
        }
                
    }
    
    public int isAmministratoreSocial(String email) {
        String sql = "SELECT email FROM AMMINISTRATORISOCIAL "
                + "WHERE email = ? ;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }
    
    public int isRegistrato(String email,String password) {
        String sql = "SELECT email FROM PERSONA "
                + "WHERE email = ? AND password = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }
}

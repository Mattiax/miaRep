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
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import pkg.oggetti.MailList;
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
        String sql = "INSERT INTO HOBBYPRATICATO(email,hobby) "
                + " VALUES (?,?);";
        if (hobby != null) {
            for (int i = 0; i < hobby.length; i++) {
                jdbcTemplate.update(sql, user, hobby[i]);
            }
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
        String sql = "INSERT INTO MESSAGGIO(mittente,destinatario,messaggio,allegato,dataOra) "
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
        String sql = "DELETE FROM MESSAGGIO "
                + "WHERE id=?;";
        jdbcTemplate.update(sql, id);
    }

    public void eliminaMessGruppo(int id) {
        String sql = "DELETE FROM MESSAGGIOGRUPPO "
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
        String sql = "SELECT * FROM PERSONA "
                + "WHERE email = '" + email + "' ";
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
                        rs.getString("foto"),
                        rs.getString("telefono"),
                        rs.getBoolean("permesso"),
                        null) : null);
        return ris;
    }

    public List<Utente> getAllUsers(String email) {
        String sql = "SELECT * FROM PERSONA WHERE "
                + "email <> '" + email + "';";
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
        String sql = "SELECT * FROM MESSAGGIO "
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
        String sql = "INSERT INTO MESSAGGIOGRUPPO(mittente,destinatario,messaggio,allegato,dataOra) "
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
        String sql = "SELECT email FROM PARTECIPANTE "
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
                + "FROM MESSAGGIOGRUPPO,PARTECIPANTE "
                + "WHERE email = '" + mittente + "' AND PARTECIPANTE.nome='" + destinatario + "' AND destinatario = '" + destinatario + "';";
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

    public void richiestaPartecipazioneGruppo(Messaggio m) {
        System.out.println(m.getDestinatario());
        String sql = "INSERT INTO RICHIESTA(descrizione,amministratore,richiedente,destinatario) "
                + " VALUES(?, (SELECT email FROM AMMINISTRATOREGRUPPO WHERE nomeGruppo = ?),?,?);";
        jdbcTemplate.update(sql, m.getMessaggio(), m.getDestinatario(), m.getMittente(), m.getDestinatario());
    }

    public void creaGruppo(String amministratore, String nome, String descrizione, String[] partecipanti) {
        String sql = "INSERT INTO GRUPPO(nome,descrizione) "
                + " VALUES (?,?);";
        jdbcTemplate.update(sql, nome, descrizione);
        sql = "INSERT INTO PARTECIPANTE(nome,email) "
                + " VALUES (?,?);";
        for (int i = 0; i < partecipanti.length; i++) {
            jdbcTemplate.update(sql, nome, partecipanti[i]);
        }
        sql = "INSERT INTO AMMINISTRATOREGRUPPO(nomeGruppo,email) "
                + " VALUES (?,?);";
        jdbcTemplate.update(sql, nome, amministratore);
    }

    public List<String> getHobbies() {
        String sql = "SELECT * FROM HOBBY;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("hobby");
            }
        });
        return ris;
    }

    public List<String> getHobbiesPersona(String utente) {
        String sql = "SELECT hobby FROM HOBBYPRATICATO "
                + "WHERE email = '" + utente + "';";
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
                + "WHERE amministratore = '" + user + "';";
        List<Richiesta> ris = jdbcTemplate.query(sql, new RowMapper<Richiesta>() {
            @Override
            public Richiesta mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Richiesta(rs.getInt("idRichiesta"), rs.getString("richiedente"), rs.getString("destinatario"), rs.getString("descrizione"));
            }
        });
        return ris;
    }

    public void approvaRichiesta(int id, String richiedente, String gruppo) {
        String sql = "DELETE FROM RICHIESTA "
                + "WHERE idRichiesta=?;";
        jdbcTemplate.update(sql, id);
        sql = "INSERT INTO PARTECIPANTE (nome,email) VALUES(?,?)";
        jdbcTemplate.update(sql, gruppo, richiedente);
    }

    public List<String> getMailList(String hobby) {
        String sql = "SELECT HOBBYPRATICATO.email "
                + "FROM HOBBYPRATICATO,PERSONA "
                + "WHERE hobby = '" + hobby + "' AND HOBBYPRATICATO.email=PERSONA.email AND permesso = 1;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("email");
            }
        });
        return ris;
    }

    public void nuovoHobby(String hobby) {
        System.out.println("agg " + hobby);
        String sql = "SELECT * FROM AMMINISTRATORESOCIAL;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("email");
            }
        });
        sql = "INSERT INTO RICHIESTAAMMINISTRATORES(richiesta,amministratore) "
                + "VALUES(?,?)";
        for (int i = 0; i < ris.size(); i++) {
            jdbcTemplate.update(sql, "Richiesta aggiunta hobby #%=" + hobby, ris.get(i));
        }

    }

    public void eliminaRichiesta(int id) {
        String sql = "SELECT * FROM AMMINISTRATORESOCIAL;";
        List<String> ris = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("email");
            }
        });
        sql = "DELETE FROM RICHIESTAAMMINISTRATORES "
                + "WHERE idRichiesta = " + id;
        for (int i = 0; i < ris.size(); i++) {
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        }
    }

    public int isAmministratoreSocial(String email) {
        String sql = "SELECT email FROM AMMINISTRATORESOCIAL "
                + "WHERE email = ? ;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int isRegistrato(String email, String password) {
        String sql = "SELECT email FROM PERSONA "
                + "WHERE email = ? AND password = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public void updateUtente(Utente u) {
        System.out.println("fddf " + u.toString());
        String sql = "UPDATE PERSONA "
                + "SET nome = '" + u.getNome() + "' , cognome = '" + u.getCognome() + "' , password = '" + u.getPassword() + "' ,indirizzo = " + (u.getIndirizzo() == null ? null : "'" + u.getIndirizzo() + "'") + " , telefono = " + (u.getTelefono() == null ? null : "'" + u.getIndirizzo() + "'") + ", permesso = '" + u.getPermesso() + "' "
                + "WHERE email = '" + u.getEmail() + "';";
        jdbcTemplate.execute(sql);
        if (u.getHobbies() != null) {
            collegaHobby(u.getEmail(), u.getHobbies());
        }
    }

    public void eliminaCollegamentoHobby(String utente, String hobby) {
        System.out.println(utente + hobby);
        String sql = "DELETE FROM HOBBYPRATICATO "
                + "WHERE email = ? AND hobby = ? ;";
        jdbcTemplate.update(sql, utente, hobby);
    }

    public List<Richiesta> getRichiesteAmm(String user) {
        System.out.println(user);
        String sql = "SELECT * FROM RICHIESTAAMMINISTRATORES "
                + "WHERE amministratore = '" + user + "';";
        List<Richiesta> ris = jdbcTemplate.query(sql, new RowMapper<Richiesta>() {
            @Override
            public Richiesta mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Richiesta(rs.getInt("idRichiesta"), null, null, rs.getString("richiesta"));
            }
        });
        return ris;
    }

    public void aggiungiHobby(int id, String hobby) {
        String sql = "INSERT INTO HOBBY (hobby) VALUES(?)";
        jdbcTemplate.update(sql, hobby);
        sql = "DELETE FROM RICHIESTAAMMINISTRATORES "
                + "WHERE idRichiesta = ?";
        jdbcTemplate.update(sql, id);
    }

    public void setImmagine(String image, String utente) {
        String sql = "UPDATE PERSONA "
                + "SET foto = '" + image + "' "
                + "WHERE email = '" + utente + "';";
        jdbcTemplate.execute(sql);
    }

    public void rimuoviGruppo(String gruppo) {
        System.out.println("gruppo eliminato" + gruppo);
        String sql = "DELETE FROM GRUPPO "
                + "WHERE nome = ?";
        jdbcTemplate.update(sql, gruppo);
        sql = "DELETE FROM MESSAGGIOGRUPPO "
                + "WHERE destinatario = ?";
        jdbcTemplate.update(sql, gruppo);
        sql = "DELETE FROM AMMINISTRATOREGRUPPO "
                + "WHERE nomeGruppo = ?;";
        jdbcTemplate.update(sql, gruppo);
        sql = "DELETE FROM PARTECIPANTE "
                + "WHERE nome = ?";
        jdbcTemplate.update(sql, gruppo);
        sql = "DELETE FROM RICHIESTA "
                + "WHERE destinatario = ?";
        jdbcTemplate.update(sql, gruppo);
    }

    public void rimuoviUtente(String utente) {
        String sql = "DELETE FROM PERSONA "
                + "WHERE email = ?";
        jdbcTemplate.update(sql, utente);
        sql = "DELETE FROM MESSAGGIO "
                + "WHERE mittente = ? OR destinatario = ?";
        jdbcTemplate.update(sql, utente, utente);
        sql = "DELETE FROM MESSAGGIOGRUPPO "
                + "WHERE mittente = ?";
        jdbcTemplate.update(sql, utente);
        sql = "DELETE FROM PARTECIPANTE "
                + "WHERE email = ?";
        jdbcTemplate.update(sql, utente);
        sql = "DELETE FROM HOBBYPRATICATO "
                + "WHERE email = ?;";
        jdbcTemplate.update(sql, utente);
        sql = "DELETE FROM AMMINISTRATOREGRUPPO "
                + "WHERE email = ?;";
        jdbcTemplate.update(sql, utente);
        sql = "DELETE FROM RICHIESTA "
                + "WHERE mittente = ?";
        jdbcTemplate.update(sql, utente);
    }

    public MailList getAllEmailsHobby() {
        String sql = "SELECT hobby,HOBBYPRATICATO.email "
                + "FROM HOBBYPRATICATO,PERSONA "
                + "WHERE HOBBYPRATICATO.email=PERSONA.email AND permesso = 1 "
                + "ORDER BY hobby;";
        List<String[]> ris = jdbcTemplate.query(sql, new RowMapper<String[]>() {
            @Override
            public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new String[]{rs.getString("hobby"), rs.getString("email")};
            }
        });
        return new MailList(ris);
    }
}

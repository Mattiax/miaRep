/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

import java.awt.Image;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author mattia.musone
 */
public class Utente {

    private String nome, cognome, telefono, email, password, indirizzo;
    private Date dataNascita;
    private char sesso;
    private String[] hobbies;
    private Image image;
    private boolean acc;

    public Utente() {
        this.nome = "";
        this.cognome = "";
        this.telefono = "";
        this.email = "";
        this.password = "";
        this.dataNascita = null;
        this.sesso = 'x';
        this.hobbies = null;
    }

    public Utente(String email, String password, String nome, String cognome, String indirizzo, char sesso, String dataNascita, Image image, String telefono, boolean acc, String[] hobbies) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        System.out.println(dataNascita);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date = formatter.parse(dataNascita);
            System.out.println(date);
            this.dataNascita=date;
        } catch (Exception ex) {
            System.out.println("errore data");
            ex.printStackTrace();
        }
        this.acc = acc;
        this.hobbies = hobbies;
        this.image = image;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDataNascita(String dataNascita) {
        try {
            this.dataNascita = new SimpleDateFormat("MM, dd, yyyy", Locale.ITALY).parse(dataNascita);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public char getSesso() {
        return sesso;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDataNascita() {
        return dataNascita;
    }
    
    public Date getDataNascitaSql(){
       return new java.sql.Timestamp(dataNascita.getTime());
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public boolean getPermesso() {
        return acc;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
}

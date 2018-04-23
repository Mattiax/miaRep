/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.io.Serializable;

/**
 *
 * @author mattia.musone
 */
public class Utente implements Serializable {

    private static final long serialVersionUID = 14849841854186L;
    private String nome, cognome, telefono, email, password, indirizzo, dataNascita;
    private char sesso;
    private String[] hobbies;
    private String image;
    private boolean acc;

    public Utente() {
        this.nome = "";
        this.cognome = "";
        this.telefono = "";
        this.email = "";
        this.password = "";
        this.dataNascita = "";
        this.sesso = 'x';
        this.hobbies = null;
    }

    public Utente(String email, String password, String nome, String cognome, String indirizzo, char sesso, String dataNascita, String image, String telefono, boolean acc, String[] hobbies) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.dataNascita = dataNascita;
        this.acc = acc;
        this.hobbies = hobbies;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
        this.dataNascita = dataNascita;
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

    public String getDataNascita() {
        return dataNascita;
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

    @Override
    public String toString() {
        return "nome " + nome + "cognome " + cognome + "tel " + telefono + "email " + email + "pass " + password + "ind " + indirizzo + "data " + dataNascita;
    }
}

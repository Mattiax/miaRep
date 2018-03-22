/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

/**
 *
 * @author MATTI
 */
public class Messaggio {

    private String mittente, destinatario, messaggio, dataOra;
    private int id;

    public Messaggio(String mittente, String destinatario, String messaggio, String dataOra) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
    }
    
    public Messaggio(int id,String mittente, String destinatario, String messaggio, String dataOra) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.messaggio = messaggio;
        this.dataOra = dataOra;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMittente() {
        return mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public String getDataOra() {
        return dataOra;
    }
}

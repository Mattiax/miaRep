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
public class Richiesta {
 
    private String richiedente,gruppo,richiesta;
    private int id;

    public Richiesta(String richiedente, String gruppo, String richiesta) {
        this.richiedente = richiedente;
        this.gruppo = gruppo;
        this.richiesta = richiesta;
    }
    
    public Richiesta(int id,String richiedente, String gruppo, String richiesta) {
        this.id=id;
        this.richiedente = richiedente;
        this.gruppo = gruppo;
        this.richiesta = richiesta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(String richiedente) {
        this.richiedente = richiedente;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public String getRichiesta() {
        return richiesta;
    }

    public void setRichiesta(String richiesta) {
        this.richiesta = richiesta;
    }
    
    
}

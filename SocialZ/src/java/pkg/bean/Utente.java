/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.bean;

import java.io.Serializable;


/**
 *
 * @author MATTI
 */
public class Utente implements Serializable{
    
    private String nome;
    private String destTemp;

    public Utente(){
        
    }
    
    public Utente(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDestTemp() {
        return destTemp;
    }

    public void setDestTemp(String destTemp) {
        this.destTemp = destTemp;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.db;

import java.util.ArrayList;
import java.util.List;
import pkg.oggetti.Messaggio;
import pkg.oggetti.Utente;

/**
 *
 * @author MATTI
 */
public interface DB {

    public void sigIn(Utente u);

    public Utente getUser(String email, String password);

    public List<Utente> getAllUsers();

    public List<Messaggio> getConversazione(String mittente, String destinatario);

    public long salvaMess(Messaggio m);

    public List<String[]> getGruppi();

    public List<Messaggio> getConversazioneGruppo(String mittente, String destinatario);

    public long aggiungiMessaggioGruppo(Messaggio m);

    public int isPartecipante(String mittente, String destinatario);

    public void richiestaPartecipazioneGruppo(Messaggio m);
    
    public void creaGruppo(String amministratore,String nome, String descrizione, String[] partecipanti);
    
    public void eliminaMessGruppo(int id);
    
    public int hasRichiestaPartecipazione(String mittente, String destinatario);
    
     public List<String> getHobbies();
     
     public List<String> getHobbiesPersona(String utente);
}

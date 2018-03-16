/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.db;

import java.util.List;
import pkg.oggetti.Utente;

/**
 *
 * @author MATTI
 */
public interface DB {

	public void sigIn(Utente u);

	public Utente getUser(String email,String password);
        
        public List<Utente> getAllUsers();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author MATTI
 */
public class UtenteValidator implements Validator{

        /**
     * N.B. Questo Validatore valida *solo*  istanze di Persona
     * La validazione avviene dopo la creazione di un una nuova Persona
     */
    public boolean supports(Class c) {
        return Utente.class.equals(c);
    }

    public void validate(Object obj, Errors e) {
    }
    
}

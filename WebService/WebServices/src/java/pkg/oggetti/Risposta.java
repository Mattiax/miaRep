/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.oggetti;

import java.io.Serializable;

/**
 *
 * @author mattia.musone
 */
public class Risposta implements Serializable{

	public static int OK = 0, UNIQUE_FAIL = 1, GENERAL_ERROR = 2;

	public Risposta() {
	}
}

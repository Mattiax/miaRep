/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

/**
 *
 * @author mattia.musone
 */
public class JavaApplication7 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		System.out.println(getHobbies());
	}

    private static String getHobbies() {
        pkg.web.Maillist_Service service = new pkg.web.Maillist_Service();
        pkg.web.Maillist port = service.getMaillistPort();
        return port.getHobbies();
    }
	
}

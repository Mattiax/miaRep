/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maillist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author mattia.musone
 */
public class MailList implements Serializable {

	LinkedHashMap<String, List<String>> mailList;

	public MailList() {
	}

	public LinkedHashMap<String, List<String>> getMailList() {
		return mailList;
	}

	public void setMailList(LinkedHashMap<String, List<String>> mailList) {
		this.mailList = mailList;
	}
}

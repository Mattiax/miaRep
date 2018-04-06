package com.example.matti.socialzandroid;

import java.util.ArrayList;
import java.util.List;

public class MailListHobby {

    private List<String> mail;
    private String hobby;

    public MailListHobby() {

    }

    public MailListHobby(List<String> mail, String hobby) {
        if (mail==null) {
            List<String> temp = new ArrayList<>();
            temp.add("Nessuno pratica questo hobby");
            this.mail = temp;
        } else {
            this.mail = mail;
        }
        this.hobby = hobby;
    }

    public List<String> getMail() {
        return mail;
    }

    public void setMail(List<String> mail) {
        this.mail = mail;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}

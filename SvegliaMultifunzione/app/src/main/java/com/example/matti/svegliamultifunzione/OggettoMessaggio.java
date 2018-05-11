package com.example.matti.svegliamultifunzione;

import android.graphics.Bitmap;

public class OggettoMessaggio {

    private String messaggio;
    private Bitmap image;

    public OggettoMessaggio(String messaggio, Bitmap image) {
        this.messaggio = messaggio;
        this.image = image;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

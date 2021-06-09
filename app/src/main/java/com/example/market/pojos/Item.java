package com.example.market.pojos;

import android.graphics.Bitmap;

public class Item {

    String title ;
    Bitmap image;
    int id ;

    public Item(Bitmap icon, String title, int id) {
        this.image = icon;
        this.title = title;
        this.id = id ;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }
}

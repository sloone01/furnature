package com.example.market.pojos;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {

    private String title;
    private String description;
    private String user;
    private float price;
    private String catagory;
    private List<String> images;
    private String id;
    private String color;
    private float oldPrice;


    public Product() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getImages() {
        if(Objects.isNull(images))
            images = new ArrayList<>();

        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public float getOldPrice() {
        return oldPrice;
    }
}

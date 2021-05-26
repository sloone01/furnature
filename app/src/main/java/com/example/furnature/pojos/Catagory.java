package com.example.furnature.pojos;


import java.io.Serializable;

public class Catagory implements Serializable {

    private String catagoryName;

    private String description;
    private String doc;

    public Catagory() {

    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  catagoryName;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }
}

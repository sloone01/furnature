package com.example.market.pojos;


import java.io.Serializable;

public class Brand implements Serializable {

    private String Name;
    private String id;

    public Brand() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }


    @Override
    public String toString() {
        return Name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.example.market.pojos;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private Product product;

    private int count;

    private float total;


    public OrderItem() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


}

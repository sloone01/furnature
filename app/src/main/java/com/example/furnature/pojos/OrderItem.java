package com.example.furnature.pojos;

import android.net.sip.SipErrorCode;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private FItem fItem;

    private int count;

    private float total;


    public OrderItem() {

    }

    public FItem getfItem() {
        return fItem;
    }

    public void setfItem(FItem fItem) {
        this.fItem = fItem;
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

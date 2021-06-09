package com.example.market.pojos;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private float totelCost;

    private String issueDate;

    private User user;

    private String address;

    private double lon;

    private double lat;

    private long contact;

    private String orderStatus;
    private List<OrderItem> items;
    private String id;
    private String username;
    private String prefTime;
    private String deliverDate;


    public Order(){
    }

    public float getTotelCost() {
        return totelCost;
    }

    public void setTotelCost(float totelCost) {
        this.totelCost = totelCost;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem>  getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPrefTime(String prefTime) {
        this.prefTime = prefTime;
    }

    public String getPrefTime() {
        return prefTime;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }
}

package com.foodapp.orhistory;

import java.time.LocalDateTime;

public class Orderhistory {
    private int orderhistoryid; // Primary key
    private int ordrid;         // Foreign key referencing orderitems table
    private int resid;          // Foreign key referencing restaurant table
    private int usid;           // Foreign key referencing user table
    private double itemtotal;   // Total amount for the order
    private String status;      // Status of the order
    private LocalDateTime orderDate; // Date and time of the order

    // Constructor
    public Orderhistory(int orderhistoryid, int ordrid, int resid, int usid, double itemtotal, String status, LocalDateTime orderDate) {
        this.orderhistoryid = orderhistoryid;
        this.ordrid = ordrid;
        this.resid = resid;
        this.usid = usid;
        this.itemtotal = itemtotal;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Orderhistory() {
        super();
    }

    // Getters and Setters
    public int getOrderhistoryid() {
        return orderhistoryid;
    }

    public void setOrderhistoryid(int orderhistoryid) {
        this.orderhistoryid = orderhistoryid;
    }

    public int getOrid() {
        return ordrid;
    }

    public void setOrid(int ordrid) {
        this.ordrid = ordrid;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public int getUsid() {
        return usid;
    }

    public void setUsid(int usid) {
        this.usid = usid;
    }

    public double getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(double itemtotal) {
        this.itemtotal = itemtotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return orderhistoryid + " " + ordrid + " " + resid + " " + usid + " " + itemtotal + " " + status + " " + orderDate;
    }
}

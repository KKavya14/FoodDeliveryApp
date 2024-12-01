package com.foodapp.orders;


import java.time.LocalDateTime;


public class Orders {
	
		    private int orderid;
		    private int userid;
		    private int restid;
		    private int menuId;
		    private int quantity;
		    private double total;
		    private LocalDateTime orders_datetime;
		    private String payment;
		    private String status;

		    public Orders(int orderid, int userid, int restid, int menuId, int quantity, double total,
		                 LocalDateTime orders_datetime, String payment, String status) {
		        this.orderid = orderid;
		        this.userid = userid;
		        this.restid = restid;
		        this.menuId = menuId;
		        this.quantity = quantity;
		        this.total = total;
		        this.orders_datetime = orders_datetime;
		        this.payment = payment;
		        this.status = status;
		    }
		    public Orders(int orderid, int userid, int restid, int menuId, int quantity, double total,
	                 String payment, String status) {
	       this.orderid = orderid;
	       this.userid = userid;
	       this.restid = restid;
	       this.menuId = menuId;
	       this.quantity = quantity;
	       this.total = total;
	      
	       this.payment = payment;
	       this.status = status;
	   }
		 public Orders(int userid, int restid, int menuId, double total, LocalDateTime orders_datetime,
					String payment, String status) {
				super();
				this.userid = userid;
				this.restid = restid;
				this.menuId = menuId;
				this.total = total;
				this.orders_datetime =orders_datetime;
				this.payment = payment;
				this.status = status;
			}
			// Getters and Setters for each field
		    public int getOrderid() {
		        return orderid;
		    }

		    public void setOrderid(int orderid) {
		        this.orderid = orderid;
		    }

		    public int getUserid() {
		        return userid;
		    }

		    public void setUserid(int userid) {
		        this.userid = userid;
		    }

		    public int getRestid() {
		        return restid;
		    }

		    public void setRestid(int restid) {
		        this.restid = restid;
		    }

		    public int getMenuId() {
		        return menuId;
		    }

		    public void setMenuId(int menuId) {
		        this.menuId = menuId;
		    }

		    public int getQuantity() {
		        return quantity;
		    }

		    public void setQuantity(int quantity) {
		        this.quantity = quantity;
		    }

		    public double getTotal() {
		        return total;
		    }

		    public void setTotal(double total) {
		        this.total = total;
		    }

		    public LocalDateTime getOrders_datetime() {
		        return orders_datetime;
		    }

		    public void setOrders_datetime(LocalDateTime ordersDatetime) {
		        this.orders_datetime= ordersDatetime;
		    }

		    public String getPayment() {
		        return payment;
		    }

		    public void setPayment(String payment) {
		        this.payment = payment;
		    }

		    public String getStatus() {
		        return status;
		    }

		    public void setStatus(String status) {
		        this.status = status;
		    }

		    @Override
		    public String toString() {
		        return orderid + " " + userid + " " + restid + " " + menuId + " " + quantity + " " +
		               total + " " + orders_datetime + " " + payment + " " + status;
		    }
		
	}



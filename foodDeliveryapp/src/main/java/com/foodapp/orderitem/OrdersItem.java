package com.foodapp.orderitem;

public class OrdersItem {
	private int orid;        
    private int orderidd;    
    private int menuid;     
    private int quantity;    
    private double price;
    private double itemtotal; 
    // Constructor
    public OrdersItem(int orid, int orderidd , int menuid, int quantity,double price, double itemtotal) {
    	this.orid=orid;
        this.orderidd = orderidd;
        this.menuid = menuid;
        this.quantity = quantity;
        this.price=price;
        this.itemtotal = itemtotal;
    }
    

    public OrdersItem(int orderidd, int menuid, int quantity, double price, double itemtotal) {
		super();
		this.orderidd = orderidd;
		this.menuid = menuid;
		this.quantity = quantity;
		this.price = price;
		this.itemtotal = itemtotal;
	}


	public OrdersItem() {
		// TODO Auto-generated constructor stub
	}
	


	public OrdersItem(int orderidd, int menuid, int quantity, double price) {
		super();
		this.orderidd = orderidd;
		this.menuid = menuid;
		this.quantity = quantity;
		this.price = price;
	}


	// Getters and Setters
    public int getOrid() {
        return orid;
    }

    public void setOrid(int orid) {
        this.orid = orid;
    }

    public int getOrderidd() {
        return orderidd;
    }

    public void setOrderidd(int orderidd) {
        this.orderidd = orderidd;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(double itemtotal) {
        this.itemtotal = itemtotal;
    }
    

    public void setPrice(double price) {
		this.price = price;
	}
    

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "OrdersItem [orid=" + orid + ", orderidd=" + orderidd + ", menuid=" + menuid + ", quantity=" + quantity
				+ ", price=" + price + ", itemtotal=" + itemtotal + "]";
	}

	
	

}

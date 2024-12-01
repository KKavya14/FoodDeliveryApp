package com.foodapp.cart;

public class CartItem {
	private int menuId;
	private int restaurantid;
	private String name;
	private int quantity;
	private int price;
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getRestaurantid() {
		return restaurantid;
	}
	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public CartItem() {
		super();
	}
	public CartItem(int menuId, int restaurantid, String name, int quantity, int price) {
		super();
		this.menuId = menuId;
		this.restaurantid = restaurantid;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	@Override
	public String toString() {
		return "CartDaoImp [menuId=" + menuId + ", restaurantid=" + restaurantid + ", name=" + name + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
	
	
	
}

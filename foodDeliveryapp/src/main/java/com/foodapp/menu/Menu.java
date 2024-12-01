package com.foodapp.menu;

public class Menu {
	private int menuId;
	private int restaurantid;
	private String name;
	private String description;
	private int price;
	private float rating;
	private String available;
	private String image;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Menu(int menuId, int restaurantid, String name, String description, int price, float rating,
			String available, String image) {
		super();
		this.menuId = menuId;
		this.restaurantid = restaurantid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.available = available;
		this.image = image;
	}
	public Menu(int menuId, int restaurantid, String name, String description, int price, float rating) {
		super();
		this.menuId = menuId;
		this.restaurantid = restaurantid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.rating = rating;
	}
	public Menu() {
		super();
		
	}
	@Override
	public String toString() {
		return  menuId + " " + restaurantid + " " + name + " "
				+ description + " " + price + " " + rating +" " +available ;
	}
	public Menu(int menuId, int restaurantid, String name, String description, int price, float rating,
			String available) {
		super();
		this.menuId = menuId;
		this.restaurantid = restaurantid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.available = available;
	}
	public Menu(int menuId, int price) {
		super();
		this.menuId = menuId;
		this.price = price;
	}
}

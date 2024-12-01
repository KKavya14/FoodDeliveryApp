package com.foodapp.restaurant;

public class Restaurant {
	private int restaurantid;
    private String name;
    private String cusineType;
    private String address;
    private int ratings;
    private String deliveryTime;
    private boolean isActive;
    private String image;
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
	public String getCusineType() {
		return cusineType;
	}
	public void setCusineType(String cusineType) {
		this.cusineType = cusineType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getRatings() {
		return ratings;
	}
	public void setRatings(int ratings) {
		this.ratings = ratings;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Restaurant(int restaurantid, String name, String cusineType, String address, int ratings,
			String deliveryTime, boolean isActive, String image) {
		super();
		this.restaurantid = restaurantid;
		this.name = name;
		this.cusineType = cusineType;
		this.address = address;
		this.ratings = ratings;
		this.deliveryTime = deliveryTime;
		this.isActive = isActive;
		this.image = image;
	}
	public Restaurant() {
		super();
		
	}
	@Override
	public String toString() {
		return  restaurantid + " " + name + " " + cusineType
				+ " " + address + " " + ratings;
	}
	public Restaurant(int restaurantid, String name, String cusineType, String address, int ratings) {
		super();
		this.restaurantid = restaurantid;
		this.name = name;
		this.cusineType = cusineType;
		this.address = address;
		this.ratings = ratings;
	}
	public Restaurant(int restaurantid, int ratings) {
		super();
		this.restaurantid = restaurantid;
		this.ratings = ratings;
	}

}

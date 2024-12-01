package com.foodapp.restaurantdao;

import java.util.List;

import com.foodapp.restaurant.Restaurant;

public interface RestaurantDao {
	 List<Restaurant> fetch();
	 void insert(Restaurant r);
	 Restaurant fetchSpecific(int i);
	 public void update(Restaurant r );
	  public int delete(int i);
}

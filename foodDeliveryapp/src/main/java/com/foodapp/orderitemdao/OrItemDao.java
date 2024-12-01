package com.foodapp.orderitemdao;

import java.util.List;

import com.foodapp.orderitem.OrdersItem;

public interface OrItemDao {
	void insert(OrdersItem item);                        // Insert a new order item
    List<OrdersItem> fetchAll();                         // Fetch all order items
    List<OrdersItem> fetchSpecific(int orid);                 // Fetch a specific order item by ID
    void update(OrdersItem item);                        // Update an existing order item
    int delete(int orid);   
}

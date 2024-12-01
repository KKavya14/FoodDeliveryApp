package com.foodapp.orderdao;

import java.util.List;

import com.foodapp.orders.Orders;

public interface OrdersDao {
	void insert(Orders order);
    List<Orders> fetchAll();
    Orders fetchSpecific(int orderId);
    void update(Orders order);
    int delete(int orderId);
}


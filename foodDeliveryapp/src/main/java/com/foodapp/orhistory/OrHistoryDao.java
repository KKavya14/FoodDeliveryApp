package com.foodapp.orhistory;

import java.util.List;

public interface OrHistoryDao {
	void insert(Orderhistory history);                   // Insert a new order history record
    List<Orderhistory> fetchAll();                       // Fetch all order history records
    Orderhistory fetchSpecific(int orderhistoryid);     // Fetch a specific order history record by ID
    void update(Orderhistory history);                   // Update an existing order history record
    int delete(int orderhistoryid);
	
}

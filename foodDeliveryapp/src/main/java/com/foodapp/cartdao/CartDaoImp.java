package com.foodapp.cartdao;

import java.util.Map;
import com.foodapp.cart.CartItem;

public class CartDaoImp {
    private Map<Integer, CartItem> items;
    
    public CartDaoImp()
    {
    	
    }

    public CartDaoImp(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Map<Integer, CartItem> addItem(CartItem item) {
        int menuId = item.getMenuId();
        if (items.containsKey(menuId)) {
            CartItem existingItem = items.get(menuId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(menuId, item);
        }
        return items;
    }
    
    public CartItem updateItem(int menuId, int quantity) {
        if (items.containsKey(menuId)) {
            if (quantity <= 0) {
                items.remove(menuId); // Remove item if quantity is zero or less
            } else {
                items.get(menuId).setQuantity(quantity);
            }
            return items.get(menuId); // Return updated or null if removed
        }
        return null;
    }

    public void deleteItem(int menuId) {
        items.remove(menuId);
    }
    public void Clear()
    {
    	items.clear();
    }
    public Map<Integer, CartItem> getItems() {
        return items;
    }

}

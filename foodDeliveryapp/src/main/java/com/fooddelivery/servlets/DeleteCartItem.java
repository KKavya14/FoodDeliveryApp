package com.fooddelivery.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodapp.cart.CartItem;
import com.foodapp.cartdao.CartDaoImp;


public class DeleteCartItem extends HttpServlet {
	 @Override
	    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        // Retrieve the menuId from the request parameter
	        int menuId = Integer.parseInt(req.getParameter("menuId"));

	        // Get the session
	        HttpSession session = req.getSession();
	        
	        // Retrieve the current cart from the session
	        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

	        if (cart != null) {
	            // Create an instance of CartDaoImp with the current cart
	            CartDaoImp cartDao = new CartDaoImp(cart);
	            
	            // Delete the item from the cart
	            cartDao.deleteItem(menuId);
	            
	            // Update the session with the modified cart
	            session.setAttribute("cart", cartDao.getItems());  // Get the updated cart
	        }

	        // Redirect the user back to the Cart page
	        resp.sendRedirect("Cart.jsp");
	    }
}

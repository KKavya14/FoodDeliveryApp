package com.foodapp.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.foodapp.cart.CartItem;
import com.foodapp.cartdao.CartDaoImp;

public class ClearCart extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the session
        HttpSession session = req.getSession();

        // Get the cart from the session
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart != null) {
            // Pass the cart to CartDaoImp and clear it
            CartDaoImp cartDao = new CartDaoImp(cart);
            cartDao.Clear();

            // Update the cart in the session
            session.setAttribute("cart", cart); // Now cart should be empty
        }

        // Redirect the user to the cart page
        resp.sendRedirect("Cart.jsp");
    }
}

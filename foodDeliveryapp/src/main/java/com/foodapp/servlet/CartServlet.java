package com.foodapp.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodapp.cart.CartItem;
import com.foodapp.cartdao.CartDaoImp;
import com.foodapp.menu.Menu;
import com.foodapp.menudaoimp.MenuDaoImp;

public class CartServlet extends HttpServlet {
    private Map<Integer, CartItem> cart;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the cart from session
        cart = (Map<Integer, CartItem>) req.getSession().getAttribute("cart");

        // If cart is null, initialize it
        if (cart == null) {
            cart = new HashMap<>();
            req.getSession().setAttribute("cart", cart);
        }

        // Retrieve parameters from the request, handling possible missing values
        String menuIdStr = req.getParameter("menuId");
        String quantityStr = req.getParameter("quantity");

        // If either parameter is missing or invalid, handle it
        if (menuIdStr != null && quantityStr != null) {
            try {
                int menuId = Integer.parseInt(menuIdStr);
                int quantity = Integer.parseInt(quantityStr);

                // Fetch menu item details
                MenuDaoImp mdaoi = new MenuDaoImp();
                Menu menu = mdaoi.fetchspecific(menuId);

                if (menu != null) {
                    CartItem cartItem = new CartItem(menuId, menu.getRestaurantid(), menu.getName(), quantity, menu.getPrice());
                    CartDaoImp cdaoi = new CartDaoImp(cart);
                    cart = cdaoi.addItem(cartItem);

                    req.getSession().setAttribute("cart", cart); // Save cart back to session
                }
            } catch (NumberFormatException e) {
                // Handle the case where parsing the menuId or quantity fails
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menuId or quantity");
                return;
            }
        } else {
            // Handle the case where parameters are missing
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "menuId and quantity are required");
            return;
        }

        // Redirect to the cart page
        resp.sendRedirect("Cart.jsp");
    }
}

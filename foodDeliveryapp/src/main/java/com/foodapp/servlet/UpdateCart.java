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

public class UpdateCart extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int menuId = Integer.parseInt(req.getParameter("menuId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        HttpSession session = req.getSession();
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart != null) {
            CartDaoImp cartDao = new CartDaoImp(cart);
            cartDao.updateItem(menuId, quantity);
            session.setAttribute("cart", cart); // Save updated cart back to session
        }
        
        resp.sendRedirect("Cart.jsp");
    }
}

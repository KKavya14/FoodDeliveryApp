package com.fooddelivery.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodapp.orderitem.OrdersItem;
import com.foodapp.oritemdaoimp.OrItemDaoImp;
import com.foodapp.menu.Menu;
import com.foodapp.menudaoimp.MenuDaoImp;

public class OrderItemDetailsServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get the orderId from the request parameter
            String orderIdStr = req.getParameter("orderId");
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid order ID");
                return;
            }

            // Parse the orderId and handle potential exceptions
            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
                return;
            }

            // Instantiate the DAO classes
            OrItemDaoImp orderItemDao = new OrItemDaoImp();
            MenuDaoImp menuDao = new MenuDaoImp();

            // Fetch order items from the database for the given orderId
            List<OrdersItem> orderItems = orderItemDao.fetchSpecific(orderId);
            System.out.println("Fetched Order Items for Order ID " + orderId + ": " + orderItems);

            if (orderItems == null || orderItems.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No order items found for this order");
                return;
            }

            // Create a Map to store menu details with menuId as the key
            Map<Integer, Menu> menuMap = new HashMap<>();

            // Loop through order items and fetch the corresponding menu details
            for (OrdersItem orderItem : orderItems) {
                // Fetch the menu item details based on menuId
                Menu menu = menuDao.fetchspecific(orderItem.getMenuid());
                if (menu != null) {
                    menuMap.put(menu.getMenuId(), menu);
                } else {
                    System.out.println("Menu item not found for menuId: " + orderItem.getMenuid());
                }
            }

            // Set the order items and menu map as request attributes
            req.setAttribute("orderItems", orderItems);
            req.setAttribute("menuMap", menuMap);

            // Forward to the JSP page
            RequestDispatcher dispatcher = req.getRequestDispatcher("orderdetails.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching order details");
        }
    }
}

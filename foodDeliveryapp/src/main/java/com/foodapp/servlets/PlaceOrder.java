package com.foodapp.servlets;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodapp.cart.CartItem;
import com.foodapp.menu.Menu;
import com.foodapp.restaurant.Restaurant;
import com.foodapp.user.User;

public class PlaceOrder extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/octjdbc";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve logged-in user
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        Integer userId = loggedInUser.getUserid();

        // Retrieve cart from session
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty");
            return;
        }

        // Retrieve MenuList and RestaurantList from session
        List<Menu> menuList = (List<Menu>) session.getAttribute("MenuList");
        if (menuList == null || menuList.isEmpty()) {
            response.sendRedirect("menu.jsp?error=menuUnavailable");
            return;
        }

        List<Restaurant> resList = (List<Restaurant>) session.getAttribute("RestaurantList");
        if (resList == null || resList.isEmpty()) {
            response.sendRedirect("home.jsp?error=noRestaurants");
            return;
        }

        // Get selected restaurant ID from request parameter
        String selectedRestIdStr = request.getParameter("restid");
        if (selectedRestIdStr == null || selectedRestIdStr.isEmpty()) {
            response.sendRedirect("home.jsp?error=missingRestaurantId");
            return;
        }

        int selectedRestId = Integer.parseInt(selectedRestIdStr);

        // Find the selected restaurant in the list
        Restaurant selectedRestaurant = resList.stream()
                .filter(res -> res.getRestaurantid() == selectedRestId)
                .findFirst()
                .orElse(null);

        if (selectedRestaurant == null) {
            response.sendRedirect("home.jsp?error=invalidRestaurant");
            return;
        }

        int restId = selectedRestaurant.getRestaurantid();

        // Generate a comma-separated list of menu IDs from the cart
        String menuIds = cart.values().stream()
                .map(item -> String.valueOf(item.getMenuId()))
                .collect(Collectors.joining(","));

        // Calculate total quantity and total price
        int totalQuantity = cart.values().stream().mapToInt(CartItem::getQuantity).sum();
        double totalAmount = cart.values().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();

        Connection con = null;
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            con.setAutoCommit(false); // Start transaction

            // Step 1: Insert into the `orders` table
            String insertOrderQuery = "INSERT INTO orders (userid, restid, menuId, total, orders_datetime, payment, status, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            int orderId = 0; // Initialize orderId for use in subsequent inserts
            try (PreparedStatement ps = con.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setInt(2, restId);
                ps.setString(3, menuIds); // Insert comma-separated menu IDs
                ps.setDouble(4, totalAmount);
                ps.setObject(5, LocalDateTime.now());
                ps.setString(6, "Card"); // Example payment method
                ps.setString(7, "Pending"); // Initial order status
                ps.setInt(8, totalQuantity); // Insert total quantity of items
                ps.executeUpdate();

                // Retrieve generated orderId
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }

            // Step 2: Insert into the `orderitems` table
            String insertOrderItemsQuery = "INSERT INTO orderitems (orderidd, menuid, quantity, price, itemtotal) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertOrderItemsQuery)) {
                for (CartItem item : cart.values()) {
                    // Validate menuId exists in MenuList
                    boolean menuExists = menuList.stream().anyMatch(menu -> menu.getMenuId() == item.getMenuId());
                    if (!menuExists) {
                        throw new SQLException("Invalid menuId in cart: " + item.getMenuId());
                    }

                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getMenuId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getPrice());
                    ps.setDouble(5, item.getQuantity() * item.getPrice());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Step 3: Insert into the `orderhistory` table
            String insertOrderHistoryQuery = "INSERT INTO orderhistory (ordrid, resid, usid, itemtotal, status, orderDate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertOrderHistoryQuery)) {
                ps.setInt(1, orderId);
                ps.setInt(2, restId);
                ps.setInt(3, userId);
                ps.setDouble(4, totalAmount);
                ps.setString(5, "Pending");
                ps.setObject(6, LocalDateTime.now()); // Set orderDate as current timestamp
                ps.executeUpdate();
            }

            // Commit transaction
            con.commit();

            // Clear the cart and redirect to success page
            session.removeAttribute("cart");
            response.sendRedirect("orderSuccess.jsp?orderId=" + orderId);

        } catch (Exception e) {
            // Rollback on failure
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            // Close database connection
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #dff0d8;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #3c763d;
            margin-bottom: 20px;
        }
        p {
            color: #555;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            background-color: #5cb85c;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #4cae4c;
        }
        .order-details {
            text-align: left;
            margin-top: 20px;
            padding: 10px;
            background: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #5cb85c;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Order Placed Successfully!</h1>
        <p>Thank you for ordering with us. Your order has been placed and is now being processed.</p>

        <% 
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr != null && !orderIdStr.isEmpty()) {
                int orderId = Integer.parseInt(orderIdStr);

                // Retrieve order details from the database
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                
                try {
                    String DB_URL = "jdbc:mysql://localhost:3306/octjdbc";
                    String DB_USERNAME = "root";
                    String DB_PASSWORD = "root";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                    // Fetch order status and other details
                    String orderQuery = "SELECT * FROM orders WHERE orderid = ?";
                    ps = con.prepareStatement(orderQuery);
                    ps.setInt(1, orderId);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        String status = rs.getString("status");
                        double totalAmount = rs.getDouble("total");
                        String paymentMethod = rs.getString("payment");
                        String orderDate = rs.getString("orders_datetime");

                        out.println("<div class='order-details'>");
                        out.println("<p><strong>Order ID:</strong> " + orderId + "</p>");
                        out.println("<p><strong>Status:</strong> " + status + "</p>");
                        out.println("<p><strong>Total Amount:</strong> $" + totalAmount + "</p>");
                        out.println("<p><strong>Payment Method:</strong> " + paymentMethod + "</p>");
                        out.println("<p><strong>Order Date:</strong> " + orderDate + "</p>");
                        out.println("</div>");

                        // Fetch order items
                        String orderItemsQuery = "SELECT oi.menuid, oi.quantity, oi.price, oi.itemtotal, m.name " +
                                                 "FROM orderitems oi " +
                                                 "JOIN menu m ON oi.menuid = m.menuid " +
                                                 "WHERE oi.orderidd = ?";
                        ps = con.prepareStatement(orderItemsQuery);
                        ps.setInt(1, orderId);
                        rs = ps.executeQuery();

                        out.println("<h3>Order Items:</h3>");
                        out.println("<table>");
                        out.println("<tr><th>Item Name</th><th>Quantity</th><th>Price</th><th>Total</th></tr>");

                        while (rs.next()) {
                            String itemName = rs.getString("name");
                            int quantity = rs.getInt("quantity");
                            double price = rs.getDouble("price");
                            double itemTotal = rs.getDouble("itemtotal");
                            out.println("<tr>");
                            out.println("<td>" + itemName + "</td>");
                            out.println("<td>" + quantity + "</td>");
                            out.println("<td>$" + price + "</td>");
                            out.println("<td>$" + itemTotal + "</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                    } else {
                        out.println("<p>No order found with ID " + orderId + ".</p>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<p>Error retrieving order details. Please try again later.</p>");
                } finally {
                    if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                    if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
            } else {
                out.println("<p>Order ID not provided.</p>");
            }
        %>

        <a href="home.jsp" class="btn">Back to Home</a>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodapp.orhistory.Orderhistory" %>
<%@ page import="com.foodapp.restaurant.Restaurant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <style>
        /* General Styles */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            color: #333;
        }

        /* Navbar Styles */
        .navbar {
            background-color: #FFA500;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar h2 {
            color: white;
            margin: 0;
        }
        .navbar a {
            background-color: white;
            color: #FFA500;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .navbar a:hover {
            background-color: #FF8C00;
            color: white;
        }

        /* Container Styles */
        .container {
            padding: 20px;
            max-width: 90%;
            margin: 20px auto;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }
        h1 {
            text-align: center;
            color: #FF8C00;
            margin-bottom: 30px;
        }

        /* Order Grid and Cards */
        .order-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }
        .order-card {
            background-color: #fff;
            border: 2px solid #FFA500;
            border-radius: 10px;
            padding: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }
        .order-card:hover {
            transform: scale(1.05);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }
        .order-card h3 {
            margin-bottom: 10px;
            color: #FF8C00;
        }
        .order-card p {
            margin: 5px 0;
            color: #555;
            font-size: 14px;
        }
        .order-card p strong {
            color: #000;
        }

        /* View Details Button */
        .order-card form button {
            padding: 10px 20px;
            background-color: #FF8C00;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.3s ease;
            font-weight: bold;
        }
        .order-card form button:hover {
            background-color: #FFA500;
            transform: scale(1.05);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .order-card {
                font-size: 16px;
            }
        }
        @media (max-width: 480px) {
            .order-card {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <div class="navbar">
        <h2>FoodApp</h2>
        <div>
            <a href="home.jsp">Home</a>
            <a href="OrderHistory.jsp">Order History</a>
        </div>
    </div>

    <!-- Container -->
    <div class="container">
        <h1>Your Order History</h1>
        <%
            // Retrieve order history and restaurant list from the session
            List<Orderhistory> orderHistoryList = (List<Orderhistory>) session.getAttribute("orderHistoryList");
            List<Restaurant> restaurantList = (List<Restaurant>) session.getAttribute("RestaurantList");
            Locale indiaLocale = new Locale("en", "IN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(indiaLocale);

            if (orderHistoryList == null || orderHistoryList.isEmpty()) {
        %>
            <p>No orders found in your history.</p>
        <%
            } else {
        %>
            <div class="order-grid">
            <%
                for (Orderhistory order : orderHistoryList) {
                    // Find the restaurant name by matching restaurant ID
                    String restaurantName = "Unknown";
                    for (Restaurant restaurant : restaurantList) {
                        if (restaurant.getRestaurantid() == order.getResid()) {
                            restaurantName = restaurant.getName();
                            break;
                        }
                    }

                    // Format the orderDate for display
                    String formattedDate = order.getOrderDate() != null
                            ? order.getOrderDate().format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))
                            : "N/A";
            %>
                <div class="order-card">
                    <h3>Order ID: <%= order.getOrid() %></h3>
                    <p><strong>Restaurant:</strong> <%= restaurantName %></p>
                    <p><strong>Total Price:</strong> <%= currencyFormatter.format(order.getItemtotal()) %></p>
                    <p><strong>Status:</strong> <%= order.getStatus() %></p>
                    <p><strong>Order Date:</strong> <%= formattedDate %></p>
                    <form action="OrderItemDetailsServlet" method="post">
                        <input type="hidden" name="orderId" value="<%= order.getOrid() %>" />
                        <button type="submit">View Details</button>
                    </form>
                </div>
            <%
                }
            %>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>

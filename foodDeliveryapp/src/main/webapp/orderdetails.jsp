<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.foodapp.orderitem.OrdersItem" %>
<%@ page import="com.foodapp.menu.Menu" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Order Details</title>
    <style>
        /* General Styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
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
            max-width: 90%;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #FF8C00;
            margin-bottom: 20px;
        }

        /* Item Styles */
        .item {
            border: 1px solid #FFA500;
            background-color: #fff;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }
        .item:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }
        .item h3 {
            margin: 0;
            font-size: 18px;
            color: #FF8C00;
        }
        .item p {
            margin: 5px 0;
            font-size: 14px;
            color: #555;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .item {
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
        <h1>Order Details</h1>
        <% 
            // Retrieve the order items and menu map from the request attributes
            List<OrdersItem> orderItems = (List<OrdersItem>) request.getAttribute("orderItems");
            Map<Integer, Menu> menuMap = (Map<Integer, Menu>) request.getAttribute("menuMap");

            if (orderItems != null && !orderItems.isEmpty()) {
                for (OrdersItem orderItem : orderItems) {
                    // Get the corresponding menu details for the current order item using the menuId
                    Menu menu = menuMap.get(orderItem.getMenuid());
        %>
        <div class="item">
            <h3>Item Name: <%= menu != null ? menu.getName() : "Unknown Item" %></h3>
            <p><strong>Description:</strong> <%= menu != null ? menu.getDescription() : "No description available" %></p>
            <p><strong>Quantity:</strong> <%= orderItem.getQuantity() %></p>
            <p><strong>Price:</strong> $<%= orderItem.getPrice() %></p>
            <p><strong>Total:</strong> $<%= orderItem.getPrice() * orderItem.getQuantity() %></p>
        </div>
        <% 
                }
            } else {
        %>
            <p>No items found for this order.</p>
        <% 
            }
        %>
    </div>
</body>
</html>

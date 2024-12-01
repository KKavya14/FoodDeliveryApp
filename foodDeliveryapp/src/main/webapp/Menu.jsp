<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.foodapp.menu.Menu" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.foodapp.cart.CartItem" %>
<%@ page import="com.foodapp.restaurant.Restaurant" %>  <!-- Import Restaurant class -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Menu Page</title>
    <style>
        /* Your existing CSS styles remain unchanged */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
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
        .container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            padding: 20px;
        }
        .card {
            background-color: #fff;
            border: 2px solid #FFA500;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin: 10px;
            width: 30%;
            padding: 20px;
            box-sizing: border-box;
            transition: transform 0.3s ease;
        }
        .card:hover {
            transform: translateY(-10px);
        }
        .card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
        }
        .card h3 {
            margin: 15px 0 10px;
            font-size: 22px;
            color: #FF8C00;
        }
        .card p {
            margin: 10px 0;
            color: #555;
        }
        .card .price {
            font-weight: bold;
            color: #FFA500;
        }
        .card .actions {
            margin-top: 15px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .card input[type="number"] {
            width: 50px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .card button {
            padding: 8px 16px;
            background-color: #FFA500;
            border: none;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .card button:hover {
            background-color: #FF8C00;
        }
        .place-order-btn {
            background-color: #28a745;
            color: white;
            padding: 15px 30px;
            font-size: 18px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
            display: inline-block;
            margin-top: 20px;
        }
        .place-order-btn:hover {
            background-color: #218838;
        }
        @media (max-width: 768px) {
            .card {
                width: 45%;
            }
        }
        @media (max-width: 480px) {
            .card {
                width: 90%;
            }
        
    </style>
    <script>
        function validateQuantity(input) {
            if (input.value <= 0) {
                alert("Quantity must be at least 1.");
                input.value = 1;
            }
        }
    </script>
</head>
<body>

<div class="navbar">
    <h2>FoodApp</h2>
    <div>
        <a href="home.jsp">Home</a>
        <a href="Cart.jsp">Cart</a>
    </div>
</div>

<div class="container">
    <%   
        List<Menu> MenuList = (List<Menu>) session.getAttribute("MenuList");
        List<Restaurant> resList = (List<Restaurant>) session.getAttribute("RestaurantList");  // Get restaurant list from session
        if (MenuList != null && !MenuList.isEmpty() && resList != null && !resList.isEmpty()) {
            Restaurant currentRestaurant = resList.get(0); // Assuming you want to display menu for the first restaurant in the list
            for (Menu menu : MenuList) {
    %>
    <div class="card">
        <% 
            String imagePath = menu.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
        %>
            <img src="<%= imagePath %>" alt="Image of <%= menu.getName() %>">
        <% 
            } else {
        %>
            <img src="menu-placeholder.jpg" alt="Placeholder image for <%= menu.getName() %>">
        <% } %>
        <h3><%= menu.getName() %></h3>
        <p><strong>Description:</strong> <%= menu.getDescription() %></p>
        <p class="price">Price: $<%= menu.getPrice() %></p>
        <p><strong>Rating:</strong> <%= menu.getRating() %> stars</p>
        <p><strong>Available:</strong> <%= menu.getAvailable() %></p>
        
        <form action="CartServlet" method="get" class="actions">
            <input type="hidden" name="menuId" value="<%= menu.getMenuId() %>">
            <input type="number" name="quantity" value="1" min="1" oninput="validateQuantity(this)">
            <button type="submit">Add to Cart</button>
        </form>
    </div>
    <% 
            }
        } else {
    %>
        <p>No menu items available for this restaurant.</p>
    <% 
        }
    %>
</div>

</body>
</html>

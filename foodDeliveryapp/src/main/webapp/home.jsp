<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.foodapp.user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foodapp.restaurant.Restaurant" %>
<!DOCTYPE html>
<html>  
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
<style>
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
    .navbar-left,
    .navbar-right {
        display: flex;
        align-items: center;
    }
    .navbar-left a {
        margin-right: 20px;
    }
    .navbar-right a {
        margin-left: 20px;
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
    .welcome {
        padding: 20px;
        background-color: #fff;
        margin: 20px auto;
        text-align: center;
        border: 2px solid #FFA500;
        border-radius: 10px;
        max-width: 80%;
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
        font-size: 24px;
        color: #FF8C00;
    }
    .card p {
        margin: 10px 0;
        color: #555;
    }
    .card .btn {
        background-color: #FFA500;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        text-align: center;
        display: inline-block;
        transition: background-color 0.3s ease;
    }
    .card .btn:hover {
        background-color: #FF8C00;
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
    }
</style>
</head>
<body>

<div class="navbar">
    <div class="navbar-left">
        <a href="home.jsp">Home</a>
    </div>
    <div class="navbar-right">
        <a href="OrderHistory">Order History</a>
        <a href="Cart.jsp">Cart</a>
    </div>
</div>

<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user != null) {
%>
<div class="welcome">
    <h1>Welcome, <%= user.getUsername() %>!</h1>
</div>
<%
    } else {
%>
<div class="welcome">
    <p>You are not logged in. Please <a href="Login.jsp">login</a>.</p>
</div>
<%
    }
%>

<div class="container">
<%
    List<Restaurant> reslist = (List<Restaurant>) session.getAttribute("RestaurantList");
    if (reslist == null || reslist.isEmpty()) {
%>
    <p>No restaurants available. Please check back later.</p>
<%
    } else {
        for (Restaurant res : reslist) {
%>
    <div class="card">
        <%
            // Check if restaurant has an image. Replace with logic to fetch actual images
            String imageUrl = "data:image/png;base64," + (res.getImage() != null ? res.getImage() : "restaurant-placeholder.jpg");
        %>
        <img src="<%= imageUrl %>" alt="Restaurant Image">
        <h3><%= res.getName() %></h3>
        <p><strong>Cuisine Type:</strong> <%= res.getCusineType() %></p>
        <p><strong>Address:</strong> <%= res.getAddress() %></p>
        <p><strong>Ratings:</strong> <%= res.getRatings() %> stars</p>
        <a href="ShowMenu?restid=<%= res.getRestaurantid() %>" class="btn">View Menu</a>
    </div>
<%
        }
    }
%>
</div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.foodapp.cart.CartItem" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
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
    .checkout-container {
        max-width: 800px;
        margin: 0 auto;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
    }
    h1 {
        color: #FF8C00;
        text-align: center;
    }
    label {
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
    }
    input, textarea {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .payment-options {
        margin-bottom: 15px;
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 10px;
    }
    .payment-option {
        display: flex;
        align-items: center;
    }
    .payment-option label {
        margin-left: 10px;
        font-weight: normal;
    }
    .btn {
        width: 100%;
        padding: 10px;
        background-color: #FF8C00;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        cursor: pointer;
    }
    .btn:hover {
        background-color: #FF4500;
    }
    .cart-summary {
        margin-bottom: 20px;
    }
    .cart-summary table {
        width: 100%;
        border-collapse: collapse;
    }
    .cart-summary th, .cart-summary td {
        padding: 10px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    .cart-summary th {
        background-color: #FFA500;
        color: white;
    }
    .total-price {
        font-weight: bold;
        font-size: 18px;
        color: #FF8C00;
        text-align: right;
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

<div class="checkout-container">
    <h1>Checkout</h1>
    
    <%
        // Retrieve the cart from session
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
    %>
        <script>
            alert("Your cart is empty. Redirecting to the home page.");
            window.location.href = "home.jsp";
        </script>
    <%
            return;
        }

        // Retrieve restaurant ID dynamically from the first item in the cart
        int restaurantId = cart.values().iterator().next().getRestaurantid(); // Assuming all items belong to the same restaurant

        // Calculate the total price of the cart
        int grandTotal = 0;
        for (CartItem item : cart.values()) {
            grandTotal += item.getPrice() * item.getQuantity();
        }
    %>

    <!-- Cart Summary Section -->
    <div class="cart-summary">
        <h2>Cart Summary</h2>
        <table>
            <tr>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total Price</th>
            </tr>
            <%
                for (CartItem item : cart.values()) {
                    int totalPrice = item.getPrice() * item.getQuantity();
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getQuantity() %></td>
                <td>$<%= item.getPrice() %></td>
                <td>$<%= totalPrice %></td>
            </tr>
            <% } %>
        </table>
        <div class="total-price">
            Grand Total: $<%= grandTotal %>
        </div>
    </div>

    <form action="PlaceOrder" method="post">
        <!-- Pass restaurant ID as a hidden input field -->
        <input type="hidden" name="restid" value="<%= restaurantId %>">

        <label for="address">Delivery Address:</label>
        <textarea id="address" name="address" rows="4" required></textarea>

        <label>Payment Method:</label>
        <div class="payment-options">
            <div class="payment-option">
                <input type="radio" id="creditCard" name="paymentMethod" value="CreditCard" required>
                <label for="creditCard">Credit Card</label>
            </div>
            <div class="payment-option">
                <input type="radio" id="debitCard" name="paymentMethod" value="DebitCard">
                <label for="debitCard">Debit Card</label>
            </div>
            <div class="payment-option">
                <input type="radio" id="netBanking" name="paymentMethod" value="NetBanking">
                <label for="netBanking">Net Banking</label>
            </div>
            <div class="payment-option">
                <input type="radio" id="cashOnDelivery" name="paymentMethod" value="CashOnDelivery">
                <label for="cashOnDelivery">Cash on Delivery</label>
            </div>
        </div>

        <!-- Place Order Button -->
        <button type="submit" class="btn">Place Order</button>
    </form>
</div>

</body>
</html>

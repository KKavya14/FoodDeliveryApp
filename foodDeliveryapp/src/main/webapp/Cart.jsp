<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.foodapp.cart.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.foodapp.restaurant.Restaurant" %> <!-- Import Restaurant class -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }
    .cart-container {
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
        background-color: #FFA500;
        color: #fff;
    }
    .total {
        font-weight: bold;
        font-size: 18px;
        color: #FF8C00;
        text-align: right;
    }
    .btn {
        padding: 5px 10px;
        margin: 5px;
        border: none;
        cursor: pointer;
        border-radius: 4px;
    }
    .btn-update {
        background-color: #4CAF50;
        color: white;
    }
    .btn-delete {
        background-color: #FF4500;
        color: white;
    }
    @media (max-width: 768px) {
        table {
            font-size: 14px;
        }
        .cart-container {
            padding: 15px;
        }
    }
</style>
<script>
    function confirmDelete() {
        return confirm('Are you sure you want to remove this item from your cart?');
    }
</script>
</head>
<body>

<div style="background-color: #FFA500; padding: 10px; display: flex; justify-content: space-between; align-items: center;">
    <h2 style="margin: 0; color: white;">FoodApp</h2>
    <div>
        <a href="home.jsp" style="background-color: white; color: #FFA500; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;">Home</a>
        <a href="Cart.jsp" style="background-color: white; color: #FFA500; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;">Cart</a>
        <form action="ClearCart" method="post" style="display: inline;">
            <button type="submit" style="background-color: #FF4500; color: white; padding: 10px 20px; border: none; border-radius: 5px; font-weight: bold; cursor: pointer;">Clear Cart</button>
        </form>
    </div>
</div>

<div class="cart-container">
    <h1>Your Cart</h1>

    <% 
        // Retrieve the cart from the session
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) { 
    %>
        <p>Your cart is empty.</p>
    <% 
        } else { 
    %>

    <table>
        <tr>
            <th>Item Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total Price</th>
            <th>Actions</th>
        </tr>

        <%
            int grandTotal = 0;
            for (CartItem item : cart.values()) {
                int totalPrice = item.getPrice() * item.getQuantity();
                grandTotal += totalPrice;
        %>
        <tr>
            <td><%= item.getName() %></td>
            <td>
                <form action="UpdateCart" method="post" style="display:inline;">
                    <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                    <select name="quantity" aria-label="Quantity for <%= item.getName() %>">
                        <% for (int i = 1; i <= 10; i++) { %>
                            <option value="<%= i %>" <%= (i == item.getQuantity()) ? "selected" : "" %>><%= i %></option>
                        <% } %>
                    </select>
                    <button type="submit" class="btn btn-update">Update</button>
                </form>
            </td>
            <td>$<%= item.getPrice() %></td>
            <td>$<%= totalPrice %></td>
            <td>
                <form action="DeleteCartItem" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                    <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                    <button type="submit" class="btn btn-delete" aria-label="Delete <%= item.getName() %>">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>

        <tr>
            <td colspan="3" class="total">Grand Total:</td>
            <td class="total">$<%= grandTotal %></td>
            <td></td>
        </tr>
    </table>

    <% 
        }
    %>
</div>

<% if (cart != null && !cart.isEmpty()) { %>
    <form action="checkout.jsp" method="get" style="text-align: center; margin-top: 20px;">
        <button type="submit" class="btn btn-update" style="padding: 10px 20px; font-size: 16px;">Proceed to Checkout</button>
    </form>
<% } %>

</body>
</html>

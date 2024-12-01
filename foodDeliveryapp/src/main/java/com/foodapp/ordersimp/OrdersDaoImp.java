package com.foodapp.ordersimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.orderdao.OrdersDao;
import com.foodapp.orders.Orders;

public class OrdersDaoImp implements OrdersDao{
	private final String INSERT_QUERY = "INSERT INTO orders(userid, restid, menuId, quantity, total, orders_datetime, payment, status) VALUES(?,?,?,?,?,?,?,?)";

    private final String FETCH_ALL_QUERY = "SELECT * FROM orders";
    private final String FETCH_SPECIFIC_QUERY = "SELECT * FROM orders WHERE orderid=?";
    private final String UPDATE_QUERY = "UPDATE orders SET status=? WHERE orderid=?";
    private final String DELETE_QUERY = "DELETE FROM orders WHERE orderid=?";
    private Connection con;

    public OrdersDaoImp () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/octjdbc", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Orders order) {
        try (PreparedStatement prep = con.prepareStatement(INSERT_QUERY)) {
            prep.setInt(1, order.getUserid());
            prep.setInt(2, order.getRestid());
            prep.setInt(3, order.getMenuId());
            prep.setInt(4, order.getQuantity());
            prep.setDouble(5, order.getTotal());
            prep.setTimestamp(6, java.sql.Timestamp.valueOf(order.getOrders_datetime())); // Set orders_datetime
            prep.setString(7, order.getPayment());
            prep.setString(8, order.getStatus());
            int status = prep.executeUpdate();
            System.out.println(status != 0 ? "Insert Success" : "Insert Failure");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Orders> fetchAll() {
        List<Orders> orders = new ArrayList<>();
        try (Statement stmt = con.createStatement(); ResultSet resultSet = stmt.executeQuery(FETCH_ALL_QUERY)) {
            while (resultSet.next()) {
                java.sql.Timestamp timestamp = resultSet.getTimestamp("orders_datetime");
                LocalDateTime orderDateTime = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                orders.add(new Orders(
                    resultSet.getInt("orderid"),
                    resultSet.getInt("userid"),
                    resultSet.getInt("restid"),
                    resultSet.getInt("menuId"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("total"),
                    orderDateTime, // Use the resolved LocalDateTime
                    resultSet.getString("payment"),
                    resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    @Override
    public Orders fetchSpecific(int orderId) {
        Orders order = null;
        try (PreparedStatement pstmt = con.prepareStatement(FETCH_SPECIFIC_QUERY)) {
            pstmt.setInt(1, orderId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                java.sql.Timestamp timestamp = resultSet.getTimestamp("orders_datetime");
                LocalDateTime orderDateTime = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                order = new Orders(
                    resultSet.getInt("orderid"),
                    resultSet.getInt("userid"),
                    resultSet.getInt("restid"),
                    resultSet.getInt("menuId"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("total"),
                    orderDateTime, // Use the resolved LocalDateTime
                    resultSet.getString("payment"),
                    resultSet.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }


    @Override
    public void update(Orders order) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {
            pstmt.setString(1, order.getStatus());
            pstmt.setInt(2, order.getOrderid());
            int result = pstmt.executeUpdate();
            System.out.println(result != 0 ? "Update Success" : "Update Failure");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(int orderId) {
        int status = 0;
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {
            pstmt.setInt(1, orderId);
            status = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}

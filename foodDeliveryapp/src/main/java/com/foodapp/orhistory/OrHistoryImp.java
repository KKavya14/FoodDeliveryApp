package com.foodapp.orhistory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrHistoryImp implements OrHistoryDao {
    private final String INSERT_QUERY = "INSERT INTO orderhistory (orderhistoryid, ordrid, resid, usid, itemtotal, status, orderDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String FETCH_SPECIFIC_QUERY = "SELECT * FROM orderhistory WHERE orderhistoryid = ?";
    private final String UPDATE_QUERY = "UPDATE orderhistory SET ordrid = ?, resid = ?, usid = ?, itemtotal = ?, status = ?, orderDate = ? WHERE orderhistoryid = ?";
    private final String DELETE_QUERY = "DELETE FROM orderhistory WHERE orderhistoryid = ?";
    private final String FETCH_ALL_QUERY = 
        "SELECT oh.*, o.orders_datetime AS orderDate " +
        "FROM orderhistory oh " +
        "JOIN orders o ON oh.ordrid = o.orderid";

    private Connection con;

    public OrHistoryImp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/octjdbc", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Orderhistory history) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {
            pstmt.setInt(1, history.getOrderhistoryid());
            pstmt.setInt(2, history.getOrid());
            pstmt.setInt(3, history.getResid());
            pstmt.setInt(4, history.getUsid());
            pstmt.setDouble(5, history.getItemtotal());
            pstmt.setString(6, history.getStatus());
            pstmt.setTimestamp(7, Timestamp.valueOf(history.getOrderDate()));
            int status = pstmt.executeUpdate();
            System.out.println(status > 0 ? "Order history inserted successfully!" : "Failure");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Orderhistory> fetchAll() {
        List<Orderhistory> histories = new ArrayList<>();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(FETCH_ALL_QUERY)) {
            while (rs.next()) {
                Orderhistory history = new Orderhistory();
                history.setOrderhistoryid(rs.getInt("orderhistoryid"));
                history.setOrid(rs.getInt("ordrid"));
                history.setResid(rs.getInt("resid"));
                history.setUsid(rs.getInt("usid"));
                history.setItemtotal(rs.getDouble("itemtotal"));
                history.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("orderDate");
                if (timestamp != null) {
                    history.setOrderDate(timestamp.toLocalDateTime());
                }
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return histories;
    }

    @Override
    public Orderhistory fetchSpecific(int orderhistoryid) {
        Orderhistory history = null;
        try (PreparedStatement pstmt = con.prepareStatement(FETCH_SPECIFIC_QUERY)) {
            pstmt.setInt(1, orderhistoryid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                history = new Orderhistory(
                        rs.getInt("orderhistoryid"),
                        rs.getInt("ordrid"),
                        rs.getInt("resid"),
                        rs.getInt("usid"),
                        rs.getDouble("itemtotal"),
                        rs.getString("status"),
                        rs.getTimestamp("orderDate").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return history;
    }

    @Override
    public void update(Orderhistory history) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {
            pstmt.setInt(1, history.getOrid());
            pstmt.setInt(2, history.getResid());
            pstmt.setInt(3, history.getUsid());
            pstmt.setDouble(4, history.getItemtotal());
            pstmt.setString(5, history.getStatus());
            pstmt.setTimestamp(6, Timestamp.valueOf(history.getOrderDate()));
            pstmt.setInt(7, history.getOrderhistoryid());
            pstmt.executeUpdate();
            System.out.println("Order history updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(int orderhistoryid) {
        int status = 0;
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {
            pstmt.setInt(1, orderhistoryid);
            status = pstmt.executeUpdate();
            if (status > 0) {
                System.out.println("Order history deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}

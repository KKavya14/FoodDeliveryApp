package com.foodapp.oritemdaoimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.orderitem.OrdersItem;
import com.foodapp.orderitemdao.OrItemDao;

public class OrItemDaoImp implements OrItemDao{
	private final String INSERT_QUERY = "INSERT INTO orderitems (orid,orderidd, menuidd, quantity,price, itemtotal) VALUES (?,?, ?, ?, ?,?)";
    private final String FETCH_ALL_QUERY = "SELECT * FROM orderitems";
    private final String FETCH_SPECIFIC_QUERY = "SELECT * FROM orderitems WHERE orderidd = ?";
    private final String UPDATE_QUERY = "UPDATE orderitems SET orderidd = ?, menuidd = ?, quantity = ?, price=?,itemtotal = ? WHERE orid = ?";
    private final String DELETE_QUERY = "DELETE FROM orderitems WHERE orid = ?";

    private Connection con;
	private int status;
	private List<OrdersItem> items;

    public OrItemDaoImp() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/octjdbc", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(OrdersItem item) {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {
        	pstmt.setInt(1, item.getOrid());
            pstmt.setInt(2, item.getOrderidd());
            pstmt.setInt(3, item.getMenuid());
            pstmt.setInt(4, item.getQuantity());
            pstmt.setDouble(5, item.getPrice());
            pstmt.setDouble(6, item.getItemtotal());
            status=pstmt.executeUpdate();
            if(status !=0)
            {
            System.out.println("Order item inserted successfully!");
            }
            else
            {
            	System.out.println("Failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrdersItem> fetchAll() {
        List<OrdersItem> items = new ArrayList<>();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(FETCH_ALL_QUERY)) {
            while (rs.next()) {
                OrdersItem item = new OrdersItem(rs.getInt("orid"),rs.getInt("orderidd"), rs.getInt("menuid"), rs.getInt("quantity"),rs.getDouble("price") ,rs.getDouble("itemtotal"));
                
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<OrdersItem> fetchSpecific(int orderId) {
        List<OrdersItem> orderItems = new ArrayList<>();
        try {
            
            PreparedStatement stmt = con.prepareStatement(FETCH_SPECIFIC_QUERY );
            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrdersItem orderItem = new OrdersItem();
                orderItem.setOrid(rs.getInt("orid"));
                orderItem.setOrderidd(rs.getInt("orderidd"));
                orderItem.setMenuid(rs.getInt("menuid"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getDouble("price"));
                orderItem.setItemtotal(rs.getDouble("itemtotal"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Fetched Order Items for Order ID " + orderId + ": " + orderItems);
        return orderItems;
    }



    @Override
    public void update(OrdersItem item) {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {
            pstmt.setInt(1, item.getOrderidd());
            pstmt.setInt(2, item.getMenuid());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setDouble(5, item.getItemtotal());
            pstmt.setInt(6, item.getOrid());
            pstmt.executeUpdate();
            System.out.println("Order item updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(int orid) {
        int status = 0;
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {
            pstmt.setInt(1, orid);
            status = pstmt.executeUpdate();
            if (status > 0) {
                System.out.println("Order item deleted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}

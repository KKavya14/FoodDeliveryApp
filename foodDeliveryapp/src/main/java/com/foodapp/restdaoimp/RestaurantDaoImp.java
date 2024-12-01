package com.foodapp.restdaoimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.restaurant.Restaurant;
import com.foodapp.restaurantdao.RestaurantDao;


public class RestaurantDaoImp implements RestaurantDao {
	private final String FETCHQUERY="select * from restaurant";
	private final String INSERT="insert into restaurant(restaurantid,name,cusineType,address,ratings)  values(?,?,?,?,?)";
	private final String FETCH_SPEC="select * from restaurant where restaurantid=?";
	private final String UPDATEQuery="update restaurant set ratings=? where restaurantid=?";
	private final String DELETE_QUERY="delete from restaurant where restaurantid=?";
	private String url="jdbc:mysql://localhost:3306/octjdbc";
	private String user="root";
	private String password="root";
	private Connection con;
	private Statement stmt;
	private ResultSet res;
    List<Restaurant> l=new ArrayList<Restaurant>();
	private PreparedStatement pstmt;
	private int status;
	private ResultSet result;
	private Restaurant restaurant;
	private List<Restaurant> r;
	
	
	

	public RestaurantDaoImp()
	{
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(url, user, password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
}

	@Override
	public List<Restaurant> fetch() {
	
		try {
			stmt=con.createStatement();
			res=stmt.executeQuery(FETCHQUERY);
			while(res.next())
			{
				l.add(new Restaurant(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5)));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return l;
		
	}

	@Override
	public void insert(Restaurant r) {
		try
		{
			pstmt=con.prepareStatement(INSERT);
			pstmt.setInt(1, r.getRestaurantid());
			pstmt.setString(2, r.getName());
			pstmt.setString(3,r.getCusineType());
			pstmt.setString(4,r.getAddress());
			pstmt.setInt(5, r.getRatings());
			
			status=pstmt.executeUpdate();
			
			if(status !=0)
			{
				System.out.println("Success");
			}
			else
			{
				System.out.println("Failure");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	List<Restaurant> extract(ResultSet result)
	{
		try {
		 while(result.next())
		 {
			 l.add(new Restaurant(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getInt(5))); 
		 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public Restaurant fetchSpecific(int i) {
		try {
				pstmt=con.prepareStatement(FETCH_SPEC);
				pstmt.setInt(1, i);
				result=pstmt.executeQuery();
				r=extract(result);
				
				if(! r.isEmpty())
				{
				restaurant=r.get(0);
				}
				else
				{
					System.out.println("No record");
					System.exit(0);
				}
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return restaurant;
}

	@Override
	public void update(Restaurant r) {
		try {
		pstmt=con.prepareStatement(UPDATEQuery);
		pstmt.setInt(1, r.getRatings());
		pstmt.setInt(2, r.getRestaurantid());
		status=pstmt.executeUpdate();
		if(status !=0)
		{
			System.out.println("Success");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int delete(int i) {
		try {
			pstmt=con.prepareStatement(DELETE_QUERY);
			pstmt.setInt(1, i);
			status=pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
}

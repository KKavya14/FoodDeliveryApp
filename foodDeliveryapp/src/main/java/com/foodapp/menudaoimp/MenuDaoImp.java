package com.foodapp.menudaoimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.menu.Menu;
import com.foodapp.menudao.MenuDao;

public class MenuDaoImp implements MenuDao {
	private final String INSERTQUERY="insert into menu(menuId,restaurantid,name,description,price,rating,avaialble) values(?,?,?,?,?,?,?)";
	private final String FETCHALL="select * from menu";
	private final String FECTHSPECIFIC="select * from menu where menuId=?";
	private final String UPDATEQUERY="update menu set price=? where menuId=?";
	private final String DELETEQUERY="delete from menu where menuId=?";
	private final String GETONRID="select * from menu where restaurantid=?";
	private String url="jdbc:mysql://localhost:3306/octjdbc";
	private String urlname="root";
	private String pwd="root";
	private Connection con;
	private PreparedStatement pstmt;
	private int status;
	private Statement stmt;
	private ResultSet resultset;
	List<Menu> menulist=new ArrayList<Menu>();
	private Menu result;
	

	public MenuDaoImp()
	{
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(url, urlname, pwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Menu m) {
		try {
		pstmt=con.prepareStatement(INSERTQUERY);
		 pstmt.setInt(1, m.getMenuId());
		 pstmt.setInt(2, m.getRestaurantid());
		 pstmt.setString(3, m.getName());
		 pstmt.setString(4, m.getDescription());
		 pstmt.setInt(5, m.getPrice());
		 pstmt.setFloat(6, m.getRating());
		 pstmt.setString(7, m.getAvailable());
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

	@Override
	public List<Menu> fetchAll() {
	    try {
	    	stmt=con.createStatement();
	    	resultset=stmt.executeQuery(FETCHALL);
	    	while(resultset.next())
	    	{
	    		menulist.add(new Menu(resultset.getInt(1),resultset.getInt(2),resultset.getString(3),resultset.getString(4),resultset.getInt(5),resultset.getFloat(6),resultset.getString(7)));
	    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return menulist;
	}
	public List<Menu> extract(ResultSet resultset)
	{
		try
		{
			while(resultset.next())
	    	{
	    		menulist.add(new Menu(resultset.getInt(1),resultset.getInt(2),resultset.getString(3),resultset.getString(4),resultset.getInt(5),resultset.getFloat(6),resultset.getString(7)));
	    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return menulist;
		
	}

	public Menu fetchspecific(int menuId) {
	    Menu menu = null;
	    try {
	       
	        PreparedStatement pstmt = con.prepareStatement(FECTHSPECIFIC);
	        pstmt.setInt(1, menuId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            menu = new Menu(rs.getInt("menuId"), rs.getInt("restaurantid"), rs.getString("name"), rs.getString("description"), rs.getInt("price"), rs.getFloat("rating"), rs.getString("available"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return menu;
	}

	@Override
	public void update(Menu m) {
		try
		{
			pstmt=con.prepareStatement(UPDATEQUERY);
			pstmt.setInt(1, m.getPrice());
			pstmt.setInt(2, m.getMenuId());
			status=pstmt.executeUpdate();
			if(status != 0)
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

	@Override
	public int delete(int i) {
	try
	{
		pstmt=con.prepareStatement(DELETEQUERY);
		pstmt.setInt(1, i);
		status=pstmt.executeUpdate();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return status;
	}

	@Override
	public List<Menu> fetchonrid(int id) {
		try {
		    pstmt=con.prepareStatement(GETONRID);
		    pstmt.setInt(1, id);
		    resultset=pstmt.executeQuery();
		    menulist=extract(resultset);
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return menulist;
	}
}

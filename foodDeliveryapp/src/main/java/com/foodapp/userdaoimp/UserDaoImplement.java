package com.foodapp.userdaoimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.user.User;
import com.foodapp.userdao.UserDao;


public class UserDaoImplement implements UserDao  {
	private final String Insert_Query="Insert into user(username,email,password,mobile) values(?,?,?,?)";
	private final String FETCH_QUERY="select * from user";
	private final String FECTH_SPECIFIC_QUERY="select * from `user` where `email`=?";
	private final String UPDATE_QUERY="update `user` set `email`=? where `userid`=?";
	private final String DELETE_QUERY="delete from `user` where `userid`=?";
    String url="jdbc:mysql://localhost:3306/octjdbc";
    String username="root";
    String password="root";
	private Connection con;
	private PreparedStatement prep;
	private int status;
	
	private Statement stmt;
	private ResultSet resultSet;
	List<User> u=new ArrayList<User>();
	private ResultSet result;
	private PreparedStatement pstmt;
	private User user;
	private int res;
	private int statuss;
	
	public UserDaoImplement()
	{
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con=DriverManager.getConnection(url, username, password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int insert(User u) {
		try {
		prep=con.prepareStatement(Insert_Query);
		prep.setString(1, u.getUsername());
		prep.setString(2, u.getEmail());
		prep.setString(3, u.getPassword());
		prep.setInt(4, u.getMobile());
		
		status=prep.executeUpdate();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<User> fetchall() {
		try {
			 stmt=con.createStatement();
			 resultSet=stmt.executeQuery(FETCH_QUERY);
			  u=extract(result);
					}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return u;
	}
	List<User> extract(ResultSet result)
	{
		try {
		 while(resultSet.next())
		 {
			 u.add(new User(resultSet.getInt("userid"),resultSet.getString("username"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getInt("mobile")));
		 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public User fetchSpecific(String email) {
		try {
			pstmt=con.prepareStatement(FECTH_SPECIFIC_QUERY);
			pstmt.setString(1,email);
			resultSet=pstmt.executeQuery();
			while(resultSet.next())
			{
				user=new User(resultSet.getInt("userid"),resultSet.getString("username"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getInt("mobile"));
			}
		}
		catch(Exception e)
		{
				e.printStackTrace();
		}
		return user;
	}

	@Override
	public void update(User u) {
     try {
    	 pstmt=con.prepareStatement(UPDATE_QUERY);
    	 pstmt.setString(1,u.getEmail());
    	 pstmt.setInt(2, u.getUserid());
    	 res=pstmt.executeUpdate();
    	 if(res !=0)
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


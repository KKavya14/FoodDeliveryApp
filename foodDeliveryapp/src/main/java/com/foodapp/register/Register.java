package com.foodapp.register;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodapp.user.User;
import com.foodapp.userdaoimp.UserDaoImplement;


public class Register extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u=new User(req.getParameter("name"),req.getParameter("email"),req.getParameter("password"),Integer.parseInt(req.getParameter("mobile")));
		UserDaoImplement udaoi=new UserDaoImplement();
		int status=udaoi.insert(u);
		
		if(status==1)
		{
			resp.sendRedirect("Login.jsp");
		}
		else
		{
			resp.sendRedirect("Failure.jsp");
		}
	}

}

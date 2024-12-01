package com.tap.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodapp.user.User;
import com.foodapp.userdaoimp.UserDaoImplement;


public class Login extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		UserDaoImplement udaoi=new UserDaoImplement();
		User u=udaoi.fetchSpecific(email);
		if(u != null)
		{
			if(u.getPassword().equals(password))
			{
				HttpSession session=req.getSession();
				session.setAttribute("loggedInUser", u);
				
				resp.sendRedirect("GetAll");
			}
			else
			{
				resp.sendRedirect("Pwinvd.jsp");
			}
		}
		else
		{
			resp.sendRedirect("Invalid.jsp");
		}
	}
}

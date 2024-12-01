package com.fooddelivery.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodapp.menu.Menu;
import com.foodapp.menudaoimp.MenuDaoImp;


public class ShowMenu extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        int rid = Integer.parseInt(req.getParameter("restid"));
	        System.out.println("Restaurant ID received: " + rid);
	        
	        MenuDaoImp mdao = new MenuDaoImp();
	        List<Menu> MenuList = mdao.fetchonrid(rid);
	        req.getSession().setAttribute("restid", rid);
	        req.getSession().setAttribute("MenuList", MenuList);
	        resp.sendRedirect("Menu.jsp?restid=" + rid); // Pass restid to Menu.jsp
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid restaurant ID");
	    }
	}


}

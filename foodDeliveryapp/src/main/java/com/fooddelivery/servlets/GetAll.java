package com.fooddelivery.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foodapp.restaurant.Restaurant;
import com.foodapp.restdaoimp.RestaurantDaoImp;


public class GetAll extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RestaurantDaoImp resim=new RestaurantDaoImp();
		List<Restaurant> rlist=resim.fetch();
		req.getSession().setAttribute("RestaurantList", rlist);
		resp.sendRedirect("home.jsp");
	}
}

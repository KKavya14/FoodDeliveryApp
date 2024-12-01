package com.foodapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodapp.orhistory.OrHistoryImp;
import com.foodapp.orhistory.Orderhistory;
import com.foodapp.user.User;


public class OrderHistory extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session
        HttpSession session = request.getSession();

        // Retrieve the logged-in user
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            // If no user is logged in, redirect to login page
            response.sendRedirect("Login.jsp");
            return;
        }

        // Fetch the order history for the logged-in user
        int userId = user.getUserid(); // Assuming the User object has a getUserid() method
        OrHistoryImp historyDao = new OrHistoryImp();

        // Filter order history specific to the user
        List<Orderhistory> orderHistoryList = historyDao.fetchAll().stream()
                .filter(history -> history.getUsid() == userId)
                .collect(Collectors.toList());

        // Save the order history in the session
        session.setAttribute("orderHistoryList", orderHistoryList);

        // Redirect to orderHistory.jsp
        response.sendRedirect("orderHistory.jsp");
    }


}

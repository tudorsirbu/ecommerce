package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.models.User;

/**
 * Servlet implementation class Home
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("user") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page //TODO change this so login isnt essential
		if (currentUser != null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect("/ecommerce/Login");
		}	
	}
}

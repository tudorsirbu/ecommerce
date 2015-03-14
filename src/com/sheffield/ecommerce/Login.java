package com.sheffield.ecommerce;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
   
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");

		// Check email and password against db 

		// If correct, load User model class for that login
		
		// Send back to jsp via request attributes, like this:		
		//request.setAttribute("user", user);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
	    requestDispatcher.forward(request, response);
	}

}
package com.sheffield.ecommerce;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
   
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//testCreateNewUser();
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		
		// get the user parameters
		String email = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");
		String password_confirmation = request.getParameter("inputPasswordConfirmation");
		String first_name = request.getParameter("inputFirstName");
		String last_name = request.getParameter("inputLastName");
		
		// start a db session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		// create a new user with the parameters received
		User user = new User();
		user.setEmail(email);
		user.setFirstName(first_name);
		user.setLastName(last_name);
		user.setPassword(password);
		
		// save the user to the database
		session.save(user);
		transaction.commit();
		
		
		requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}

}
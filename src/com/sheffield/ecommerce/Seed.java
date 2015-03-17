package com.sheffield.ecommerce;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
   
public class Seed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createTestUser();
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}
	

	private void createTestUser(){
		// start a db session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		// create a new user with the parameters received
		User user = new User();
		user.setEmail("john.doe@test.co.uk");
		user.setFirstName("john");
		user.setLastName("doe");
		user.setPassword("password");
		
		// save the user to the database
		session.save(user);
		session.getTransaction().commit();
	}
}
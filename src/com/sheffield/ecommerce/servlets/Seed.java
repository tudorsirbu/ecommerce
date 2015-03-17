package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sheffield.ecommerce.exceptions.*;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class Seed extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			createTestUser();
		} catch (InvalidModelException | ConnectionProblemException ex) {
			request.setAttribute("errorMsg", ex.getMessage());
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}
	

	private void createTestUser() throws InvalidModelException, ConnectionProblemException{
		// start a db session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		
		try {
			// create a new user with the parameters received
			User user = new User();
			user.setEmail("john.doe@test.co.uk");
			user.setFirstName("john");
			user.setLastName("doe");
			user.setPassword("password");
			user.validate();
			
			// save the user to the database
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (InvalidModelException ex) {
			//TODO log
			throw ex;
		} catch (Exception ex) {
			//TODO log
			session.getTransaction().rollback();
			throw new ConnectionProblemException("Error connecting to the database.");
		}

	}
}
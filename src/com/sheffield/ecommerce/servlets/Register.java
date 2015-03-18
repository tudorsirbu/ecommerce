package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.Session;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		
		//Start a database session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		
		
		
		if (!arePasswordsMatching(request)){
			request.setAttribute("errorMsg", "Password and confirmation do not match!");
			requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		try {		
			//Create a new user with received user data
			User user = new User();
			user.setFirstName(request.getParameter("inputFirstName"));
			user.setLastName(request.getParameter("inputLastName"));
			user.setEmail(request.getParameter("inputEmail"));
			user.setPassword(request.getParameter("inputPassword"), request.getParameter("inputPasswordConfirmation"));
			
			//Save the user to the database
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			LOGGER.log(Level.FINE, "New user registered with email: " + user.getEmail());
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			request.setAttribute("errorMsg", ex.getMessage());
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			request.setAttribute("errorMsg", "A connection problem occurred.");
		}
		
		requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
//		response.sendRedirect("ecommerce/Login");
	}
	
	private boolean arePasswordsMatching(HttpServletRequest request){
		return request.getParameter("inputPassword").equals(request.getParameter("inputPasswordConfirmation"));
	}
	
}
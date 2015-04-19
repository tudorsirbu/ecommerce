package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("user") : null;
	    
	    //If a user is already logged in we redirect them to the homepage
		if (currentUser != null) {
			response.sendRedirect("/ecommerce/Home");
		} else {
			//Otherwise the user is not logged in and they are allowed to register
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		
		if (!arePasswordsMatching(request)){
			request.setAttribute("errorMsg", "Password and confirmation do not match");
			requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		
		//Start a database session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		
		try {		
			//Create a new user with received user data
			User user = new User();
			user.setFirstName(request.getParameter("inputFirstName"));
			user.setLastName(request.getParameter("inputLastName"));
			user.setEmail(request.getParameter("inputEmail"));
			PasswordHelper passwordHelper = new PasswordHelper(request.getParameter("inputPassword"));
			user.setPasswordHash(passwordHelper.getPasswordHash());
			user.setPasswordSalt(passwordHelper.getPasswordSalt());
			user.validateModel();
			
			//Save the user to the database
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			LOGGER.log(Level.FINE, "New user registered with email: " + user.getEmail());
			requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
			requestDispatcher.forward(request, response);
			return;
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			request.setAttribute("errorMsg", ex.getMessage());
		} catch (HibernateException ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
			session.getTransaction().rollback();
			request.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			request.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
		}
		
		requestDispatcher = request.getRequestDispatcher("jsp/register.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private boolean arePasswordsMatching(HttpServletRequest request){
		return request.getParameter("inputPassword").equals(request.getParameter("inputPasswordConfirmation"));
	}
	
}
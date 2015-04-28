package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.User;
   
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
	private UserDao dao;
	
	public Login() {
		// Create a new instance of the data access object when the servlet is initialised
		dao = new UserDao();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	    
		//If a user is already logged in we redirect them to the homepage
		if (currentUser != null) {
			response.sendRedirect("/ecommerce/Home");
		} else {
			//Otherwise the user is not logged in and they are allowed to register
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//Get the email and password from the login form
		String email = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");

		// Attempt to get the user object
		User currentUser = dao.getUserByEmail(email);
		
		//If no user is found then display an error
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		if (currentUser == null) {
			request.setAttribute("errorMsg", "Email address not recognised");	
			requestDispatcher.forward(request, response);
		} else {
			//Otherwise get the user object and authenticate the password
			boolean validPassword;
			try {
				validPassword = PasswordHelper.verifyPassword(password, currentUser.getPasswordSalt(), currentUser.getPasswordHash());
				//If the password is valid, add the user object to the session
				if (validPassword) {
					HttpSession httpSession = request.getSession(true); //Returns a new session if one does not exist
					httpSession.setAttribute("currentUser", currentUser);
					response.sendRedirect("/ecommerce/Home");
				} else {
					//Otherwise display an error
					request.setAttribute("errorMsg", "Invalid password");
					requestDispatcher.forward(request, response);
				}
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
				request.setAttribute("errorMsg", "Unable to verify password");
				requestDispatcher.forward(request, response);
			}
		}
	}
}

package com.sheffield.ecommerce.servlets.users;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.servlets.Register;

/**
 * Servlet implementation class Home
 */
public class UserEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());
	private UserDao dao;
	
	public UserEditor() {
		dao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Attempt to get the current user
			HttpSession httpSession = request.getSession(false);
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
		    //If a user is logged in show the homepage, otherwise direct them to the login page
			if (currentUser != null) {			
				// Edit page
				int id = Integer.parseInt(request.getParameter("id"));
				User user = dao.getUserById(id);
				
				if (currentUser.getRole() == User.EDITOR || currentUser.getId() == id) {	
					
					if (user != null) { 
						request.setAttribute("user", user);		
					} else {
						request.setAttribute("errorMsg", "No user exists with this id.");
					}
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
					requestDispatcher.forward(request, response);
						
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
				}
			} else {
				response.sendRedirect("/ecommerce/Login");
			}	
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("errorMsg", "No user exists with this id.");
			response.sendRedirect("/ecommerce/users");
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		User user = null;
		try {			
			//Attempt to get the current user
			HttpSession httpSession = request.getSession(false);
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
			// Get the id parameter from the URL
		    int id = Integer.parseInt(request.getParameter("id"));
				
		    //If a user is logged in show the homepage, otherwise direct them to the login page
			if (currentUser != null && (currentUser.getRole() == User.EDITOR || currentUser.getId() == id)) {

				// Update
				user = dao.getUserById(id);
				user.setFirstName(request.getParameter("firstName"));
				user.setLastName(request.getParameter("lastName"));
				user.setEmail(request.getParameter("email"));
				if (currentUser.getRole() == User.EDITOR) {
					user.setRole(Integer.parseInt(request.getParameter("role")));
				}
				// Check if a new password was entered 
				if (request.getParameter("password") != null && !request.getParameter("password").equals("")) {
					// Check if the passwords matched
					if (!request.getParameter("password").equals(request.getParameter("passwordConfirmation"))){
						request.setAttribute("errorMsg", "Password and confirmation do not match");
						request.setAttribute("user", user);
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
						requestDispatcher.forward(request, response);
						return;
					} else {
						PasswordHelper passwordHelper = new PasswordHelper(request.getParameter("password"));
						user.setPasswordHash(passwordHelper.getPasswordHash());
						user.setPasswordSalt(passwordHelper.getPasswordSalt());
						dao.updateUserWithPassword(user);
					}
					
				} else {
					dao.updateUser(user);
				}

				if (currentUser.getId() == id) {
					httpSession.setAttribute("currentUser", user);
				}
				
				if (currentUser.getRole() == User.EDITOR) {
					response.sendRedirect("/ecommerce/users");
				} else {
					response.sendRedirect("/ecommerce/");
				}
					
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			}	
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			request.setAttribute("errorMsg", "No user exists with this id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		} catch (InvalidModelException e) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, e.getMessage());
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("user", user);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			//If there was an error updating the password, show an error
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("errorMsg", "There was an error updating the password");
			request.setAttribute("user", user);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}

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

/**
 * Servlet for the user edit page
 */
public class UserEditor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserEditor.class.getName());
	private UserDao dao;
	
	public UserEditor() {
		// Create a new instance of the data access object when the servlet is initialised
		dao = new UserDao();
	}

	/**
	 * Handle GET requests for the user edit page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);
		try {
			//Attempt to get the current user from the session
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
		    //If a user is not logged in, direct them to the login page
			if (currentUser != null) {			
				// Get the user from the id in the request parameters
				int id = Integer.parseInt(request.getParameter("id"));
				User user = dao.getUserById(id);
				
				// Only allow editors and the current user to access their edit page
				if (currentUser.getRole() == User.EDITOR || currentUser.getId() == id) {	
					
					// Send the user object to the page if they exist
					// Otherwise, display an error
					if (user != null) { 
						request.setAttribute("user", user);		
					} else {
						httpSession.setAttribute("errorMsg", "No user exists with this id.");
					}
					
					// Display the edit form
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
					requestDispatcher.forward(request, response);
						
				} else {
					// Display a 404 error if the user is not permitted to view this page
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
				}
			} else {
				// Redirect to the login page if the user is not logged in
				response.sendRedirect(request.getContextPath() + "/Login");
			}	
		} catch (NumberFormatException e) {
			// Display an error if the requested user does not exist
			LOGGER.log(Level.SEVERE, e.getMessage());
			httpSession.setAttribute("errorMsg", "No user exists with this id.");
			response.sendRedirect(request.getContextPath() + "/users");
		}
	}
	
	/**
	 * Handle POST requests for the user edit page
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		User user = null;
		HttpSession httpSession = request.getSession(false);
		try {			
			//Attempt to get the current user from the session
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
			// Get the id parameter from the URL
		    int id = Integer.parseInt(request.getParameter("id"));
				
		    // Only allow logged in editors and the current user to make changes to this user
			if (currentUser != null && (currentUser.getRole() == User.EDITOR || currentUser.getId() == id)) {

				// Update the user's parameters
				user = dao.getUserById(id);
				user.setFirstName(request.getParameter("firstName"));
				user.setLastName(request.getParameter("lastName"));
				user.setEmail(request.getParameter("email"));
				
				// If the current user is an editor, ensure that the role is updated
				if (currentUser.getRole() == User.EDITOR) {
					user.setRole(Integer.parseInt(request.getParameter("role")));
				}
				
				// Check if a new password was entered 
				if (request.getParameter("password") != null && !request.getParameter("password").equals("")) {
					// If the passwords don't match, display an error
					if (!request.getParameter("password").equals(request.getParameter("passwordConfirmation"))){
						httpSession.setAttribute("errorMsg", "Password and confirmation do not match");
						request.setAttribute("user", user);
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
						requestDispatcher.forward(request, response);
						return;
						
					} else {
						// If the passwords do match, update the user object and save the changes to the database
						PasswordHelper passwordHelper = new PasswordHelper(request.getParameter("password"));
						user.setPasswordHash(passwordHelper.getPasswordHash());
						user.setPasswordSalt(passwordHelper.getPasswordSalt());
						dao.updateUserWithPassword(user);
					}
				} else {
					// If no password was entered, update the user in the database normally
					dao.updateUser(user);
				}
				
				// If the current user is being updated, also update the current user in the session
				if (currentUser.getId() == id) {
					httpSession.setAttribute("currentUser", user);
				}
				
				// If the current user is an editor, redirect to the users index page
				// Otherwise redirect to the root
				if (currentUser.getRole() == User.EDITOR) {
					response.sendRedirect(request.getContextPath() + "/users");
				} else {
					response.sendRedirect(request.getContextPath() + "/Home");
				}
					
			} else {
				// Display a 404 error if the user is not permitted to view this page
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			}	
			
		} catch (NumberFormatException e) {
			// Display an error if the requested user does not exist
			LOGGER.log(Level.WARNING, e.getMessage());
			httpSession.setAttribute("errorMsg", "No user exists with this id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		} catch (InvalidModelException e) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, e.getMessage());
			httpSession.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("user", user);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			//If there was an error updating the password, show an error
			LOGGER.log(Level.SEVERE, e.getMessage());
			httpSession.setAttribute("errorMsg", "There was an error updating the password");
			request.setAttribute("user", user);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/users/form.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}

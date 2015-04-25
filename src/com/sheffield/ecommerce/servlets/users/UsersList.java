package com.sheffield.ecommerce.servlets.users;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.servlets.Register;

/**
 * Servlet for the user index and show pages
 */

public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());
	private UserDao dao;
	
	public UsersList() {
		// Create a new instance of the data access object when the servlet is initialised
		dao = new UserDao();
	}
	
	/**
	 * Handle GET requests for the user edit page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Attempt to get the current user from the session
			HttpSession httpSession = request.getSession(false);
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
		    // If a user is not logged in, direct them to the login page
			if (currentUser != null) {
				
				// Get the user id from the request parameters
				String idString = request.getParameter("id");
				
				// Check if the id was present
				if (idString != null && !idString.equals("")) {
					// Parse the id
					int id = Integer.parseInt(idString);
					
					// Check that the current user is an editor, or that they are trying to view their own show page
					if (currentUser.getRole() == User.EDITOR || (currentUser.getRole() != User.EDITOR && currentUser.getId() == id)) {
						// Get the user from the id
						User user = dao.getUserById(id);
						
						// Send the user object to the page
						// If the user is null, display an error
						if (user != null) { 
							request.setAttribute("user", user);
						} else {
							request.setAttribute("errorMsg", "No user exists with this id.");
						}
						
						// Redirect to the show page for this user
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/show.jsp");
						requestDispatcher.forward(request, response);
						
					} else {
						// Display a 404 error if the user is not permitted to view this page
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
					}

				// If the id was not present, we want the index page for users
				} else {
					// Check that the current user is an editor
					if (currentUser.getRole() == User.EDITOR) {
						// Get a list of all the users
						request.setAttribute("users", dao.getAllUsers());
						
						// Redirect to the index page
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/list.jsp");
						requestDispatcher.forward(request, response);
					} else {
						// Display a 404 error if the user is not permitted to view this page
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
					}
				}
			} else {
				// If the user is not logged in, redirect them to the login page
				response.sendRedirect("/ecommerce/Login");
			}	
		} catch (NumberFormatException e) {
			// Display an error if the requested user does not exist
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("errorMsg", "No user exists with this id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/list.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}

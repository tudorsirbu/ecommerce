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

public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());
	private UserDao dao;
	
	public UsersList() {
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Attempt to get the current user
			HttpSession httpSession = request.getSession(false);
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("user") : null;
			
		    //If a user is logged in show the homepage, otherwise direct them to the login page
			if (currentUser != null) {
				
				// Get the id parameter from the URL
				String idString = request.getParameter("id");
				
				// Check if the id was present
				if (idString != null && !idString.equals("")) {
					int id = Integer.parseInt(idString);
					
					if (currentUser.getRole() != User.EDITOR && currentUser.getId() == id) {
						// Show page
						User user = dao.getUserById(id);
						
						if (user != null) { 
							request.setAttribute("user", user);
						} else {
							request.setAttribute("errorMsg", "No user exists with this id.");
						}
						
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/show.jsp");
						requestDispatcher.forward(request, response);
						
					} else {
						// Authors can only view their own profile
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
					}

				} else {
					if (currentUser.getRole() == User.EDITOR) {
						// Index page
						request.setAttribute("users", dao.getAllUsers());
						
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/list.jsp");
						requestDispatcher.forward(request, response);
					} else {
						// Only editors can access the user list
						response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
					}
				}
			} else {
				response.sendRedirect("/ecommerce/Login");
			}	
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			request.setAttribute("errorMsg", "No user exists with this id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/list.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}

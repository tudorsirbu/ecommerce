package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

/**
 * Servlet implementation class Home
 */
public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("user") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page //TODO change this so login isnt essential
		if (currentUser != null) {
			System.out.println(currentUser.getRole());
			System.out.println(User.EDITOR);
			if (currentUser.getRole() == User.EDITOR) {
				
				request.setAttribute("users", getAllUsers());
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/users/list.jsp");
				requestDispatcher.forward(request, response);
				
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			}
		} else {
			response.sendRedirect("/ecommerce/Login");
		}	
	}
	
	private List<User> getAllUsers() {
		//Start a database session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM User");
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		return results;
	}
}

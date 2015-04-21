package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.Query;
import org.hibernate.Session;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

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

		//Attempt to get a user from the database with that email
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "FROM User u WHERE u.email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		
		//If no user is found then display an error
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		if (results.isEmpty()) {
			request.setAttribute("errorMsg", "Email address not recognised");	
			requestDispatcher.forward(request, response);
		} else {
			//Otherwise get the user object and authenticate the password
			User currentUser = results.get(0);
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

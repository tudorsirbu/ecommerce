package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		String email = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");

		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "FROM User u WHERE u.email = :email AND u.password = :password";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		
		if (results.isEmpty()) {
			request.setAttribute("errorMsg", "Incorrect email or password");
			requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
			
		} else {
			User user = results.get(0);
			request.setAttribute("user", user);
			requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
		}
		
		requestDispatcher.forward(request, response);
	}

}
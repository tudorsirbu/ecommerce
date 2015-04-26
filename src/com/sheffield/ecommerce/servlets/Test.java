package com.sheffield.ecommerce.servlets;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

public class Test extends HttpServlet{
	private static final long serialVersionUID = 8774299340041837105L;
	private static final Logger LOGGER = Logger.getLogger(Register.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Start a database session
				Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
				
				try {		
					//Create a new user with received user data
					User author = new User();
					author.setEmail("tudor.sirbu@gmail.com");
					author.setFirstName("Tudor");
					author.setLastName("Sirbu");
					author.setPasswordHash("password");
					author.setPasswordSalt("password");
					author.setRole(0);
					
					Article article = new Article();
					article.setTitle("My article");
					article.setArticle_abstract("My abstract");
					article.setAuthor(author);
					
					Article article1 = new Article();
					article1.setTitle("My article1");
					article1.setArticle_abstract("My abstract1");
					article1.setAuthor(author);
					
					Article article2 = new Article();
					article2.setTitle("My article2");
					article2.setArticle_abstract("My abstract2");
					article2.setAuthor(author);
					
					User user = new User();
					user.setEmail("tudor.sirbu@gmail.com");
					user.setFirstName("Tudor");
					user.setLastName("Sirbu");
					user.setPasswordHash("password");
					user.setPasswordSalt("password");
					user.setRole(0);
					
					HashSet<Article> articlesToReview = new HashSet<Article>();
					articlesToReview.add(article);
					articlesToReview.add(article1);
					articlesToReview.add(article2);
					
					user.setArticlesToReview(articlesToReview);
					
					//Save the user to the database
					session.beginTransaction();
					session.save(author);
					session.save(article);
					session.save(article1);
					session.save(article2);
					session.save(user);
					session.getTransaction().commit();
					
					return;
				} catch (HibernateException ex) {
					//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
					LOGGER.log(Level.SEVERE, ex.getMessage());
					session.getTransaction().rollback();
					request.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
				} catch (Exception ex) {
					//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
					LOGGER.log(Level.SEVERE, ex.getMessage());
					session.getTransaction().rollback();
					request.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
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

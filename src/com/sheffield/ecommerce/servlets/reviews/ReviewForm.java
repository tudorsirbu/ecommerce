package com.sheffield.ecommerce.servlets.reviews;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

   
public class ReviewForm extends HttpServlet{
	private static final long serialVersionUID = -1473286239109165384L;
	private static final Logger LOGGER = Logger.getLogger(ReviewForm.class.getName());
	private UserDao dao = new UserDao();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
				HttpSession httpSession = request.getSession(false);
			    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			    
				//If a user is not logged in or they are not an author(reviwer)
				if (currentUser == null) {
					//They are not authorised to view this page
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/403.jsp");
					requestDispatcher.forward(request, response);
				} else {
					//Otherwise the user is shown the review submission page
					Set<Article> articles = dao.getArticlesToReview(currentUser.getId());
					request.setAttribute("articles", articles);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp"); 
					requestDispatcher.forward(request, response);
				}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
		//Start a database session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
	
		try {		
			//Create a new review with received user data
			Review review = new Review();
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("articleToReview")));
			review.setArticle(article);
			review.setOverallJudgement(request.getParameter("overallJudgement"));
			review.setReviewerExpertise(request.getParameter("reviewerExpertise"));
			review.setArticleSummary(request.getParameter("articleSummary"));
			review.setSubstantiveCriticism(request.getParameter("articleCriticism"));
			review.setSmallErrors(request.getParameter("articleErrors"));
			review.setCommentsForEditor(request.getParameter("secretComments"));
			
			
			
			//Save the review to the database
			session.beginTransaction();
			dao.deleteReviewedArticle(article,currentUser);
			session.save(review);
			session.getTransaction().commit();
			
			Mailer.sendEmail(currentUser, "Review Submission Successfull", "You have successfully submited a review for the article with the title:"+article.getTitle());
			request.setAttribute("successMsg", "Review submitted successfully!");
	        requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
			requestDispatcher.forward(request, response);
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
		
		requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp");
		requestDispatcher.forward(request, response);
	}
	
}

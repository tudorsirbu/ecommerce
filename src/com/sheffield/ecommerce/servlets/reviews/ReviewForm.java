package com.sheffield.ecommerce.servlets.reviews;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.Mailer;
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
			Article article = null;
			//Create a new review with received user data
			Review review = new Review();
			if(request.getParameter("articleToReview") != null){
				article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("articleToReview")));
				review.setArticle(article);
			}
			review.setOverallJudgement(request.getParameter("overallJudgement"));
			review.setReviewerExpertise(request.getParameter("reviewerExpertise"));
			review.setArticleSummary(request.getParameter("articleSummary"));
			review.setSubstantiveCriticism(request.getParameter("articleCriticism"));
			review.setSmallErrors(request.getParameter("articleErrors"));
			review.setCommentsForEditor(request.getParameter("secretComments"));
			review.validateModel();
			
			
			//Save the review to the database
			session.beginTransaction();
			//No longer delete the link between user and article until the article has been published.
			//If the article has already been revised, when submittin the second review, delete the link beetween reviewer and article
			
			if(article != null)
				if(article.getTitle().toLowerCase().contains("(revised)"))
					dao.deleteReviewedArticle(article,currentUser);
			session.save(review);
			session.getTransaction().commit();
			
			Mailer.sendEmail(currentUser, "Review Submission Successfull", "You have successfully submited a review for the article with the title:"+article.getTitle());
			httpSession.setAttribute("successMsg", "Review submitted successfully!");
			LOGGER.log(Level.FINE, "New review submitted for article: " + article.getTitle());
	        requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
			requestDispatcher.forward(request, response);
			return;
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			httpSession.setAttribute("errorMsg", ex.getMessage()); 
		} catch (HibernateException ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			httpSession.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			httpSession.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
		}
		
		requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp");
		requestDispatcher.forward(request, response);
	}
	
}

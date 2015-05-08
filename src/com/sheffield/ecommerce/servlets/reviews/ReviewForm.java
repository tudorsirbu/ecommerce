package com.sheffield.ecommerce.servlets.reviews;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.HibernateException;
import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.ReviewDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.User;

   
public class ReviewForm extends HttpServlet{
	private static final long serialVersionUID = -1473286239109165384L;
	private static final Logger LOGGER = Logger.getLogger(ReviewForm.class.getName());
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
				HttpSession httpSession = request.getSession(true);
			    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			    
				//If a user is not logged in or they are not an author(reviwer)
				if (currentUser == null) {
					//They are not authorised to view this page
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/403.jsp");
					requestDispatcher.forward(request, response);
				} else {
					
					// Try to fetch the article
					int articleId = 0;
					try {
						articleId = Integer.parseInt(request.getParameter("article_id"));
					} catch (Exception e) {
						// Display an error if the article doesn't exist
						httpSession.setAttribute("errorMsg", "No article exists with that id.");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp"); 
						requestDispatcher.forward(request, response);
						return;
					}
					
					Article article = ArticleDao.getArticleById(articleId);
					if (article == null) {
						// Display an error if the article doesn't exist
						httpSession.setAttribute("errorMsg", "No article exists with that id.");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp"); 
						requestDispatcher.forward(request, response);
						return;
					}
					
					//Throw an error if the article has already been reviewed or if it has been revised and already been reviewed
					ReviewDao reviewDao = new ReviewDao();
					List<Review> currentArticleReviews = reviewDao.getReviewsForUserAndArticle(currentUser, article);
					if ((article.getNumberOfRevisions() == 0 && currentArticleReviews.size() > 0) || (article.getNumberOfRevisions() == 1 && currentArticleReviews.size() != 1)) {
						httpSession.setAttribute("errorMsg", "This article cannot be reviewed at this time.");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp"); 
						requestDispatcher.forward(request, response);
						return;
					}
						
					//Otherwise the user is shown the review submission page
					request.setAttribute("article", article);
					if (currentArticleReviews.size() > 0) {
						request.setAttribute("lastReview", currentArticleReviews.get(0));
					}
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp"); 
					requestDispatcher.forward(request, response);
				}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	
		try {	
			int articleId = Integer.parseInt(request.getParameter("article_id"));
			Article article = ArticleDao.getArticleById(articleId);
			//Create a new review with received user data
			Review review = new Review();
			review.setArticle(article);
			review.setOverallJudgement(request.getParameter("overallJudgement"));
			review.setReviewerExpertise(request.getParameter("reviewerExpertise"));
			review.setArticleSummary(request.getParameter("articleSummary"));
			review.setSubstantiveCriticism(request.getParameter("articleCriticism"));
			review.setSmallErrors(request.getParameter("articleErrors"));
			review.setCommentsForEditor(request.getParameter("secretComments"));
			review.setRejectReason(request.getParameter("rejectReason"));
			review.setReviewer(currentUser);				
			
			if(article != null)
				if(article.getNumberOfRevisions() == 1 && ArticleDao.getReviewers(article.getId()).contains(currentUser)){
					UserDao dao = new UserDao();
					dao.deleteReviewedArticle(article,currentUser);
				}
	
			ReviewDao reviewDao = new ReviewDao();
			reviewDao.addReview(review);

			
			Mailer.sendEmail(currentUser, "Review Submission Successfull", "You have successfully submited a review for the article with the title:"+article.getTitle());
			httpSession.setAttribute("successMsg", "Review submitted successfully!");
			LOGGER.log(Level.FINE, "New review submitted for article: " + article.getTitle());
			response.sendRedirect(request.getContextPath() + "/Home");
			return;
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			ex.printStackTrace();
			httpSession.setAttribute("errorMsg", ex.getMessage()); 
		} catch (HibernateException ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			httpSession.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			httpSession.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
		}
		
		requestDispatcher = request.getRequestDispatcher("jsp/review/review_form.jsp");
		requestDispatcher.forward(request, response);
	}
	
}

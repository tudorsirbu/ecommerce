package com.sheffield.ecommerce.servlets.reviews;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ReviewDao;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.User;

public class RejectReview extends HttpServlet {
	
	private static final long serialVersionUID = 3272403543196162754L;
	private ReviewDao dao = new ReviewDao();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null && currentUser.getId() == 1) {
			Review review = ReviewDao.getReviewById(Integer.parseInt(request.getParameter("reviewId")));
			dao.deleteReview(Integer.parseInt(request.getParameter("reviewId")));
			
			httpSession.setAttribute("successMsg", "Review was successfully rejected! The reviewer has been notified.");
			Mailer.sendEmail(review.getReviewer(), "Your review for article: " + review.getArticle().getTitle(), "The editor has rejected your review. Please put in a proper effort when reviewing an article.");
			response.sendRedirect(request.getContextPath() + "/article/show?article_id="+review.getArticle().getId());	
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

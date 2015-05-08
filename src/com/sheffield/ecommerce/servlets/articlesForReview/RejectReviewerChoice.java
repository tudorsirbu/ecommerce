package com.sheffield.ecommerce.servlets.articlesForReview;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class RejectReviewerChoice extends HttpServlet {
	private static final long serialVersionUID = -2438158205701866930L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null && currentUser.getId() == 1) {
			// get the articles for the current user
			User user = UserDao.getUserById(Integer.parseInt(request.getParameter("reviewer_id")));
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("article_id")));
			UserDao.deleteReviewedArticle(article, user);
			httpSession.setAttribute("successMsg", "Reviewer's choice rejected! The reviewer has been notified.");
			Mailer.sendEmail(user, "Your article for review", "The editor has rejected your choice of article to review. Please select another article.");
			response.sendRedirect(request.getContextPath() + "/Home"); 
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

package com.sheffield.ecommerce.servlets.articles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.ReviewDao;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Article;

public class ArticleShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null ) {
			int articleId = Integer.parseInt(request.getParameter("article_id"));
				boolean downloadable = ArticleDao.doesArticleBelongToUser(articleId, currentUser);
				
				Article article = ArticleDao.getArticleById(articleId);
				List <Review> reviews = ReviewDao.getReviewsForArticle(article.getId());
				List <User> reviewers = ArticleDao.getReviewers(articleId); 
				if(currentUser.getId() == 1) {
					request.setAttribute("editor",true);
				request.setAttribute("current_user", currentUser );

				}
				request.setAttribute("reviewers", reviewers );
				request.setAttribute("article", article);
				request.setAttribute("author", article.getAuthor());
				request.setAttribute("reviews", reviews);
				request.setAttribute("downloadable", downloadable);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/articles/show.jsp");
				requestDispatcher.forward(request, response);
			
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

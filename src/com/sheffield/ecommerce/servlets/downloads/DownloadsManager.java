package com.sheffield.ecommerce.servlets.downloads;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class DownloadsManager extends HttpServlet {
	private UserDao dao = new UserDao();
	
	private static final long serialVersionUID = 3350776160576001632L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null) {
			// get the articles for the current user
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("article_id")));
			int articlesToReviewSize = dao.getArticlesToReview(currentUser.getId()).size();
			if(articlesToReviewSize <3){
				dao.setArticleToReview(article, currentUser);
				response.sendRedirect(request.getContextPath()+"/uploads/"+article.getFileName());
				request.setAttribute("successMsg", "Article draft downloaded successfully!");
				return;
			}else{
				request.setAttribute("errorMsg", "You already downloaded three articles for review! Please finish reviewing those before downloading other articles!");
				
			}
			
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/articlesForReview/listArticlesForReview.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect("/ecommerce/Login");
		}
	}
}

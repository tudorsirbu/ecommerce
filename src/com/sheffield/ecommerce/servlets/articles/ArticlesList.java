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
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Article;

public class ArticlesList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null) {
			// get the articles for the current user
			List<Article> articles = ArticleDao.getArticlesForUser(currentUser);
			
			request.setAttribute("articles", articles);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/articles/list.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

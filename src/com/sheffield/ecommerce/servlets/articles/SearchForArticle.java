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

public class SearchForArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the articles for the current user
		String query = request.getParameter("inputTitle");

		List<Article> articles = ArticleDao.getAllArticles();
		if(query != null && !query.isEmpty()){
			articles = ArticleDao.getArticlesWithTitle(query);
		}
			
			
		request.setAttribute("articles", articles);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/articles/search_results.jsp");
		requestDispatcher.forward(request, response);
	}
}

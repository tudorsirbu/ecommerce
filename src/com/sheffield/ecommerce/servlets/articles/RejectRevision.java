package com.sheffield.ecommerce.servlets.articles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class RejectRevision extends HttpServlet {
	
	private static final long serialVersionUID = 3405920947838606963L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null && currentUser.getId() == 1) {
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("articleId")));
			ArticleDao.rejectRevision(article);
			
			httpSession.setAttribute("successMsg", "Revision was successfully rejected! The author has been notified.");
			Mailer.sendEmail(article.getAuthor(), "Your revision for article: " + article.getTitle(), "The editor has rejected your revision. Please get in touch with the editor at: " + currentUser.getEmail());
			response.sendRedirect(request.getContextPath() + "/article/show?article_id="+article.getId());	
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

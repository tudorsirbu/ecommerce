package com.sheffield.ecommerce.servlets.downloads;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.ReviewDao;
import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.servlets.UploadArticle;

public class DownloadsManager extends HttpServlet {
	private UserDao dao = new UserDao();
	
	private static final long serialVersionUID = 3350776160576001632L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		if (currentUser != null) {
			// get the articles for the current user
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("article_id")));
			int articlesToReviewSize = dao.getArticlesToReview(currentUser.getId()).size();
			if(articlesToReviewSize <3){
				ReviewDao reviewDao = new ReviewDao();
				if (!reviewDao.isUserReviewingArticle(currentUser,article.getId())) {
					// Assign user to review this article if not done so already
					dao.setArticleToReview(article, currentUser);
				}
				
				// Get the file from the tmp folder and send it to the user
				String filename = article.getLatestFileName();
			    File file = new File(UploadArticle.UPLOAD_PATH, filename);
			    response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			    response.setHeader("Content-Length", String.valueOf(file.length()));
			    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			    Files.copy(file.toPath(), response.getOutputStream());

				return;
			}else{
				httpSession.setAttribute("errorMsg", "You already downloaded three articles for review! Please finish reviewing those before downloading other articles!");	
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/articlesForReview/listArticlesForReview.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}
}

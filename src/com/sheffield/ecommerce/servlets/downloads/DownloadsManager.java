package com.sheffield.ecommerce.servlets.downloads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(DownloadsManager.class.getName());
	
	private static final long serialVersionUID = 3350776160576001632L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is logged in show the homepage, otherwise direct them to the login page
		try {
			// get the articles for the current user
			Article article = ArticleDao.getArticleById(Integer.parseInt(request.getParameter("article_id")));
			
			// If published
			if (article.getEdition() != null) {
				loadFile(article.getLatestFileName(), response);
				return;
			
			} else {
				// Not published
				if (currentUser == null) {
					// If not logged in, show an error
					httpSession.setAttribute("errorMsg", "You are not permitted to download this file.");
		            response.sendRedirect(request.getContextPath() + "/Home");
					return;
				}
				
				if (currentUser.getRole() == User.EDITOR || article.getAuthor().getId() == currentUser.getId()) {
					// If an editor or author of this article, allow download
					loadFile(article.getLatestFileName(), response);
					return;
				}
				
				// Otherwise check review status			
				int articlesToReviewSize = UserDao.getArticlesToReview(currentUser.getId()).size();
				if(articlesToReviewSize <3){
					if (!ReviewDao.isUserReviewingArticle(currentUser,article.getId())) {
						// Assign user to review this article if not done so already
						UserDao.setArticleToReview(article, currentUser);
					}
	
					loadFile(article.getLatestFileName(), response);
					return;
					
				}else{
					httpSession.setAttribute("errorMsg", "You already downloaded three articles for review! Please finish reviewing those before downloading other articles!");	
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/articlesForReview/listArticlesForReview.jsp");
				requestDispatcher.forward(request, response);
			}
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			httpSession.setAttribute("errorMsg", "Error: That article does not exist.");
            response.sendRedirect(request.getContextPath() + "/Home");
    		return;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			httpSession.setAttribute("errorMsg", "Error accessing uploaded file. The file may not exist.");
            response.sendRedirect(request.getContextPath() + "/Home");
    		return;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			httpSession.setAttribute("errorMsg", "There was an error downloading this file.");
            response.sendRedirect(request.getContextPath() + "/Home");
    		return;
		}
	}
	
	private void loadFile(String filename, HttpServletResponse response) throws IOException {
    	// Get the file from the tmp folder and send it to the user
	    File file = new File(UploadArticle.UPLOAD_PATH, filename);
	    response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	    response.setHeader("Content-Length", String.valueOf(file.length()));
	    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		Files.copy(file.toPath(), response.getOutputStream());
	}
}

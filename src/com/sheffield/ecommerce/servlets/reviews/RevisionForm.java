package com.sheffield.ecommerce.servlets.reviews;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.dao.ReviewDao;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class RevisionForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "uploads";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private ServletFileUpload upload;
    private String uploadPath;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	    
		if (currentUser == null) {
			// Redirect to the login if the user is not logged in
			response.sendRedirect("/ecommerce/Login");
			return;
		}
		
		int articleId = 0;
		try {
			articleId = Integer.parseInt(request.getParameter("articleId"));
		} catch (Exception e) {
			// Display an error if the article doesn't exist
			request.setAttribute("errorMsg", "No article exists with that id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp"); 
			requestDispatcher.forward(request, response);
			return;
		}
		
		Article article = ArticleDao.getArticleById(articleId);
		if (article == null) {
			// Display an error if the article doesn't exist
			request.setAttribute("errorMsg", "No article exists with that id.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp"); 
			requestDispatcher.forward(request, response);
			return;
		}
		
		if (currentUser.getRole() != User.AUTHOR || currentUser.getId() != article.getAuthor().getId()) {
			// Display a 403 error if the user is not permitted to view this page
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			return;
		}
		
		if (article.getNumberOfRevisions() >= 2) {
			// Display an error if there has already been two revisions
			request.setAttribute("errorMsg", "This article has already been revised twice and cannot be revised again.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp"); 
			requestDispatcher.forward(request, response);
			return;
		}
		
		ReviewDao reviewDao = new ReviewDao();
		if (reviewDao.getReviewsForArticle(articleId).size() % 3 != 0) {
			// Display an error if less than 3 reviews have been submitted for this article
			request.setAttribute("errorMsg", "This article does not have enough reviews to be revised.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp"); 
			requestDispatcher.forward(request, response);
			return;
		}

		//Otherwise the user is shown the revision page
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp"); 
		requestDispatcher.forward(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher;
		
		// Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			    
		if (currentUser == null || currentUser.getRole() != User.AUTHOR) {
			// Display a 403 error if the user is not permitted to view this page
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			return;
		}
		
		// checks if the request actually contains a file
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }
        
        // configure & initialize the file upload
        initUpload();
        
        String articleId = null;
        String revisionDetails = null;
        String fileName = null;
        
        try {
        	// parses the request's content to extract file data
            List<FileItem> formItems = upload.parseRequest(request);      	 
 
            // iterates over form's fields
            for (FileItem item : formItems) {
            	String fieldName = item.getFieldName();
            	
            	// processes only fields that are not form fields
                if (!item.isFormField()) {
                	// generate the file name
                	String currentTimestamp = String.valueOf(System.currentTimeMillis());
                	String extension = getFileExtension(item.getName());
                	
                	if(!extension.toLowerCase().equals("pdf")){
                		request.setAttribute("errorMsg", "Uploaded article needs to be a PDF.");
                        requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp");
                		requestDispatcher.forward(request, response);
                		return;
                	}
                	
                    fileName = new File(currentTimestamp + "." + extension).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);

                    // saves the file on disk
                    item.write(storeFile);
                } else {
                	if (fieldName.equals("revisionDetails")) {
                		revisionDetails = item.getString();
                	} else if (fieldName.equals("articleId")) {
                		articleId = item.getString();
                	}
                }
            }
            
        	// Find the article to be revised
        	Article article = ArticleDao.getArticleById(Integer.parseInt(articleId));
        	
            // Update the correct column in the database
            if (article.getFileNameRevision1() == null || article.getFileNameRevision1().equals("")) {
            	article.setFileNameRevision1(fileName);
            	article.setRevisionDetails1(revisionDetails);
            } else {
            	article.setFileNameRevision2(fileName);
            	article.setRevisionDetails2(revisionDetails);
            }
        			
            ArticleDao.reviseArticle(article);
            
        } catch (Exception ex) {
            request.setAttribute("errorMsg", ex.getMessage());
            requestDispatcher = request.getRequestDispatcher("jsp/review/revision_form.jsp");
    		requestDispatcher.forward(request, response);
    		return;
        }

        request.setAttribute("successMsg", "Article revised!");
        requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
        requestDispatcher.forward(request, response);
	}
	
	private void initUpload(){
		// configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
         
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
	}
	
	/**
	 * Extracts the extensions of a given file name
	 * @param fileName the file name for which the extension is required
	 * @return the extension (e.g. jpg)
	 */
	private String getFileExtension(String fileName){
		String[] terms = fileName.split("\\.");
		return terms[terms.length-1];
	}

}
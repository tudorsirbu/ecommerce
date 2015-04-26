package com.sheffield.ecommerce.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;


public class UploadArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UploadArticle.class
			.getName());

	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "uploads";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private ServletFileUpload upload;
    private String uploadPath;
    
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// checks if there is a user logged in
		if(isUserLoggedIn(request, response) != null){
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload_article.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.sendRedirect("/ecommerce/Login");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher;
		User currentUser = isUserLoggedIn(request, response);
		
		// checks if there is a user logged in and if 
		// the user is not logged in send him/her to login page
		if(currentUser == null){
			response.sendRedirect("/ecommerce/Login");
		}

		// checks if the request actually contains a file
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }
        
        // configure & initialize the file upload
        initUpload();
        
        // instantiate a new article
        Article article = new Article();
        
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            // iterates over form's fields
            for (FileItem item : formItems) {
            	String fieldName = item.getFieldName();
                
            	// processes only fields that are not form fields
                if (!item.isFormField()) {
                	// generate the file name
                	String currentTimestamp = String.valueOf(System.currentTimeMillis());
                    String fileName = new File(currentTimestamp + "." + getFileExtension(item.getName())).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    LOGGER.log(Level.INFO, filePath);
                    File storeFile = new File(filePath);

                    // saves the file on disk
                    item.write(storeFile);
                    article.setFileName(fileName);
                }else{
                	LOGGER.log(Level.INFO, fieldName + " => " + item.getString());
                	if(fieldName.equals("inputTitle"))
                		article.setTitle(item.getString());
                	else if(fieldName.equals("inputArticleAbstract"))
                		article.setArticle_abstract(item.getString());
                }
            }
            article.setMain_contact_id(currentUser.getId());
            LOGGER.log(Level.INFO, article.toString());
            ArticleDao.addArticle(article);
        } catch (Exception ex) {
            request.setAttribute("errorMsg", ex.getMessage());
            requestDispatcher = request
    				.getRequestDispatcher("jsp/upload_article.jsp");
    		requestDispatcher.forward(request, response);
    		return;
        }

        request.setAttribute("successMsg", "Article submitted successfully!");
        requestDispatcher = request.getRequestDispatcher("jsp/welcome.jsp");
        requestDispatcher.forward(request, response);
	}
	
	
	private Article createArticleFromRequestParams(HttpServletRequest request){
		Article article = new Article();
		
		return article;
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
	
	private User isUserLoggedIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(false);
		return (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	}
}
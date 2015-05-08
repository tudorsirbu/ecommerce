package com.sheffield.ecommerce.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sheffield.ecommerce.dao.ArticleDao;
import com.sheffield.ecommerce.helpers.Mailer;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;


public class UploadArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UploadArticle.class.getName());

	public static final String UPLOAD_PATH = "/tmp";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    private ServletFileUpload upload;
    
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		//Attempt to get the current user from the session
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
		if(currentUser != null){
			RequestDispatcher requestDispatcher;
			if(currentUser.getRole() == 0){
				requestDispatcher = request.getRequestDispatcher("jsp/upload_article.jsp");
				requestDispatcher.forward(request, response);
				return;
			} else { 
				httpSession.setAttribute("errorMsg", "You do not have the necessary rights to upload articles.");
				response.sendRedirect(request.getContextPath() + "/Home");
				return;
			}
			
		} else {
			response.sendRedirect(request.getContextPath() + "/Login");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher;
		//Attempt to get the current user from the session
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
		// checks if there is a user logged in and if 
		// the user is not logged in send him/her to login page
		if(currentUser == null){
			response.sendRedirect(request.getContextPath() + "/Login");
		}

		// checks if the request actually contains a file
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }
        
        // configure & initialize the file upload
        initUpload();
        
        // instantiate a new article
        Article article = new Article();
        
String testFileName = "";
        
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
                		httpSession.setAttribute("errorMsg", "Uploaded article needs to be a PDF.");
                        requestDispatcher = request.getRequestDispatcher("jsp/upload_article.jsp");
                		requestDispatcher.forward(request, response);
                		return;
                	}
                	
                    String fileName = new File(currentTimestamp + "." + extension).getName();
                    String filePath = UPLOAD_PATH + File.separator + fileName;
                    File storeFile = new File(filePath);
                    
testFileName = fileName;

                    // saves the file on disk
                    item.write(storeFile);
                    article.setFileName(fileName);
                }else{
                	LOGGER.log(Level.INFO, fieldName + " => " + item.getString());
                	if(fieldName.equals("inputTitle"))
                		article.setTitle(item.getString());
                	else if(fieldName.equals("inputArticleAbstract"))
                		article.setArticle_abstract(item.getString());
                	else if(fieldName.equals("inputAuthors"))
                		article.setOtherAuthors(item.getString());;
                }
            }
            article.setAuthor(currentUser);
            LOGGER.log(Level.INFO, article.toString());
            ArticleDao.addArticle(article);
        } catch (Exception ex) {
        	httpSession.setAttribute("errorMsg", ex.getMessage());
            requestDispatcher = request
    				.getRequestDispatcher("jsp/upload_article.jsp");
    		requestDispatcher.forward(request, response);
    		return;
        }

        Mailer.sendEmail(currentUser, "Article uploaded successfully", "Your article has been uploaded successfully and it will soon be reviewed by other authors. \n Thank you!");
httpSession.setAttribute("successMsg", "Article submitted successfully! - " + testFileName);
		response.sendRedirect(request.getContextPath() + "/Home");
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
         
        // creates the directory if it does not exist
        File uploadDir = new File(UPLOAD_PATH);
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
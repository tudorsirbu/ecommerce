package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
   
public class UploadArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UploadArticle.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/upload_article.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		RequestDispatcher requestDispatcher;
		
		
	}
}
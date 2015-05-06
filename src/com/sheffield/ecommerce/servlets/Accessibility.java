package com.sheffield.ecommerce.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Accessibility
 */
public class Accessibility extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Accessibility() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/accessibility.jsp");
		requestDispatcher.forward(request, response);
	}

}

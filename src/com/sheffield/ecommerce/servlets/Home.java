package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sheffield.ecommerce.dao.JournalDao;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;
/**
 * Servlet implementation class Home
 */
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    JournalDao journalDao = new JournalDao();
		Journal journal = journalDao.getJournal();
		List<Volume> volumes = journalDao.getAllVolumesWithEditions();
		
		request.setAttribute("volumes", volumes);
		request.setAttribute("journal", journal);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/published_articles.jsp");
		requestDispatcher.forward(request, response);
	}
}

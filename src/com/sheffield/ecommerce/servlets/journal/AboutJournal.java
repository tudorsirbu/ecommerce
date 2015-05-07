package com.sheffield.ecommerce.servlets.journal;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sheffield.ecommerce.dao.JournalDao;
import com.sheffield.ecommerce.models.User;

/**
 * Servlet implementation class AboutJournal
 */
public class AboutJournal extends HttpServlet {
	private static final long serialVersionUID = 4127538126197499187L;
	private JournalDao dao; //Data access object
	
    public AboutJournal() {
        super();
		dao = new JournalDao();
	}   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	    
		//If a user is not logged in or they are not an editor
		if (currentUser == null) {
			//They are not authorised to view this page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/403.jsp");
			requestDispatcher.forward(request, response);
		} else {
			//Otherwise the editor is shown the manage journal page
			request.setAttribute("journal", dao.getJournal());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/journal/about.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}

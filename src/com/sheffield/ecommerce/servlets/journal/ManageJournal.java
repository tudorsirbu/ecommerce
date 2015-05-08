package com.sheffield.ecommerce.servlets.journal;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.sheffield.ecommerce.dao.JournalDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

public class ManageJournal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ManageJournal.class.getName());


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	    
		//If a user is not logged in or they are not an editor
		if (currentUser == null || currentUser.getRole() != User.EDITOR) {
			//They are not authorised to view this page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/403.jsp");
			requestDispatcher.forward(request, response);
		} else {
			//Otherwise the editor is shown the manage journal page
			request.setAttribute("journal", JournalDao.getJournal());
			request.setAttribute("volumes", JournalDao.getAllVolumesWithEditions());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/journal/manage.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//Attempt to get the current user
		HttpSession httpSession = request.getSession(true);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
	    
		//If a user is not logged in or they are not an editor
		if (currentUser == null || currentUser.getRole() != User.EDITOR) {
			//They are not authorised to view this page
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/403.jsp");
			requestDispatcher.forward(request, response);
		} else {
			//Otherwise perform the update
			
			//Start a database session
			Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
			
			try {		
				//Create a new model with received data
				Journal journal = new Journal();
				journal.setTitle(request.getParameter("journalTitle"));
				journal.setAcademicAims(request.getParameter("journalAims"));
				journal.setSubmissionGuidelines(request.getParameter("submissionGuidelines"));
				journal.validateModel();
				
				//Update database
				JournalDao.updateJournal(journal);
				LOGGER.log(Level.FINE, "Journal updated");	
				
			} catch (InvalidModelException ex) {
				//If there was any invalid information then log and throw the message up to the user
				LOGGER.log(Level.INFO, ex.getMessage());
				httpSession.setAttribute("errorMsg", ex.getMessage());
			} catch (HibernateException ex) {
				//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
				LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
				session.getTransaction().rollback();
				httpSession.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
			} catch (Exception ex) {
				//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
				LOGGER.log(Level.SEVERE, ex.getMessage());
				session.getTransaction().rollback();
				httpSession.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
			}
			//Otherwise the editor is shown the manage journal page
			httpSession.setAttribute("successMsg", "Journal updated successfully");
			request.setAttribute("journal", JournalDao.getJournal());
			request.setAttribute("volumes", JournalDao.getAllVolumesWithEditions());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/journal/manage.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}

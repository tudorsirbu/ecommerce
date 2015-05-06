package com.sheffield.ecommerce.servlets.journal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.sheffield.ecommerce.dao.JournalDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Edition;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;

/**
 * Servlet for the edition editor
 */
public class EditionEditor extends HttpServlet {
	private static final long serialVersionUID = 6252143328801068461L;
	private static final Logger LOGGER = Logger.getLogger(EditionEditor.class.getName());
	private JournalDao dao;
	
	public EditionEditor() {
		// Create a new instance of the data access object when the servlet is initialised
		dao = new JournalDao();
	}

	/**
	 * Handle GET requests for the edition editor
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Attempt to get the current user from the session
		HttpSession httpSession = request.getSession(false);
	    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
		
	    //If a user is not logged in, direct them to the login page
		if (currentUser != null) {			
			
			// Only allow editors to access the page
			if (currentUser.getRole() == User.EDITOR) {	
				//If a id is present, get the object to edit it. Otherwise make a instance
				if (request.getParameterMap().containsKey("id")) {
					// Get the edition from the id in the request parameters
					int id = Integer.parseInt(request.getParameter("id"));
					Edition edition = dao.getEditionById(id);
					
					// Send the edition object to the page if it exists
					// Otherwise, display an error
					if (edition != null) { 
						request.setAttribute("edition", edition);
						
						// Display the edit form
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/journal/editionForm.jsp");
						requestDispatcher.forward(request, response);
					} else {
						httpSession.setAttribute("errorMsg", "No edition exists with this id.");
						response.sendRedirect(request.getContextPath() + "/ManageJournal");
					}	
				} else {
					//If we are making a new edition we need to know which volume it should below too
					if (request.getParameterMap().containsKey("vol")) {
						// Display the edit form
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/journal/editionForm.jsp");
						requestDispatcher.forward(request, response);
					} else {
						httpSession.setAttribute("errorMsg", "Unknown volume.");
						response.sendRedirect(request.getContextPath() + "/ManageJournal");	
					}
				}
			} else {
				// Display a 404 error if the user is not permitted to view this page
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			}
		} else {
			// Redirect to the login page if the user is not logged in
			response.sendRedirect(request.getContextPath() + "/Login");
		}	

	}
	
	/**
	 * Handle POST requests for the edition editor
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession httpSession = request.getSession(false);

		try {
			//Attempt to get the current user from the session
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
		    //If a user is not logged in, direct them to the login page
			if (currentUser != null) {			
				
				// Only allow editors to access the page
				if (currentUser.getRole() == User.EDITOR) {	
					String urlString = "ManageJournal";
					if (request.getParameterMap().containsKey("id")) {
						// Get the edition from the id in the request parameters
						int id = Integer.parseInt(request.getParameter("id"));
						Edition edition = dao.getEditionById(id);
						
						if (edition != null) { 
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date date = format.parse(request.getParameter("publicationDate"));
							edition.setPublicationDate(date);
							dao.updateEdition(edition);
							urlString = "VolumeEditor?id=" + edition.getVolume().getVolumeId();
						} else {
							httpSession.setAttribute("errorMsg", "Unable to edit edition with this id.");
						}
					} else {
						// Get the edition from the id in the request parameters
						int vol = Integer.parseInt(request.getParameter("vol"));
						Volume volume = dao.getVolumeById(vol);
						
						// Send the edition object to the page if it exists
						// Otherwise, display an error
						if (volume != null) { 
							Edition edition = new Edition();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date date = format.parse(request.getParameter("publicationDate"));
							edition.setPublicationDate(date);
							edition.setVolume(volume);
							dao.addNewEdition(edition);
							urlString = "VolumeEditor?id=" + vol;
						} else {
							httpSession.setAttribute("errorMsg", "No volume exists with this id.");
							response.sendRedirect(request.getContextPath() + "/ManageJournal");
						}	
					}
					response.sendRedirect(request.getContextPath() + "/" + urlString);
				} else {
					// Display a 404 error if the user is not permitted to view this page
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
				}
			} else {
				// Redirect to the login page if the user is not logged in
				response.sendRedirect(request.getContextPath() + "/Login");
			}
		} catch (InvalidModelException | ParseException ex) {
			//If there was any invalid model information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			httpSession.setAttribute("errorMsg", ex.getMessage());
			response.sendRedirect(request.getContextPath() + request.getServletPath() + request.getQueryString());
		} catch (HibernateException ex) {
			//If an unexpected error occurred then log, throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
			httpSession.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
			response.sendRedirect(request.getContextPath() + "/ManageJournal");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			httpSession.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
			response.sendRedirect(request.getContextPath() + "/ManageJournal");
		}
		
	}
}

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
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;

/**
 * Servlet for the volume editor
 */
public class VolumeEditor extends HttpServlet {
	private static final long serialVersionUID = 6252143328801068461L;
	private static final Logger LOGGER = Logger.getLogger(VolumeEditor.class.getName());
	private JournalDao dao;
	
	public VolumeEditor() {
		// Create a new instance of the data access object when the servlet is initialised
		dao = new JournalDao();
	}

	/**
	 * Handle GET requests for the volume editor
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
					// Get the volume from the id in the request parameters
					int id = Integer.parseInt(request.getParameter("id"));
					Volume volume = dao.getVolumeById(id);
					
					// Send the volume object to the page if they exist
					// Otherwise, display an error
					if (volume != null) { 
						request.setAttribute("volume", volume);
						
						// Display the edit form
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/journal/volumeForm.jsp");
						requestDispatcher.forward(request, response);
					} else {
						request.setAttribute("errorMsg", "No volume exists with this id.");
						response.sendRedirect("/ecommerce/ManageJournal");
					}	
				} else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/journal/volumeForm.jsp");
					requestDispatcher.forward(request, response);
				}
			} else {
				// Display a 404 error if the user is not permitted to view this page
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
			}
		} else {
			// Redirect to the login page if the user is not logged in
			response.sendRedirect("/ecommerce/Login");
		}	

	}
	
	/**
	 * Handle POST requests for the volume editor
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			//Attempt to get the current user from the session
			HttpSession httpSession = request.getSession(false);
		    User currentUser = (httpSession != null) ? (User) httpSession.getAttribute("currentUser") : null;
			
		    //If a user is not logged in, direct them to the login page
			if (currentUser != null) {			
				
				// Only allow editors to access the page
				if (currentUser.getRole() == User.EDITOR) {	
					if (request.getParameterMap().containsKey("id")) {
						// Get the volume from the id in the request parameters
						int id = Integer.parseInt(request.getParameter("id"));
						Volume volume = dao.getVolumeById(id);
						
						if (volume != null) { 
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							Date date = format.parse(request.getParameter("publicationDate"));
							volume.setPublicationDate(date);
							dao.updateVolume(volume);
						} else {
							request.setAttribute("errorMsg", "Unable to edit volume with this id.");
						}
					} else {
						Volume volume = new Volume();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						Date date = format.parse(request.getParameter("publicationDate"));
						volume.setPublicationDate(date);
						dao.addNewVolume(volume);
					}
					response.sendRedirect("/ecommerce/ManageJournal");
				} else {
					// Display a 404 error if the user is not permitted to view this page
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user is not permitted to access this page.");
				}
			} else {
				// Redirect to the login page if the user is not logged in
				response.sendRedirect("/ecommerce/Login");
			}
		} catch (InvalidModelException | ParseException ex) {
			//If there was any invalid model information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			request.setAttribute("errorMsg", ex.getMessage());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/journal/volumeForm.jsp");
			requestDispatcher.forward(request, response);
		} catch (HibernateException ex) {
			//If an unexpected error occurred then log, throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
			request.setAttribute("errorMsg", "The data entered is invalid, please check and try again.");
			response.sendRedirect("/ecommerce/ManageJournal");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			request.setAttribute("errorMsg", "A problem occurred and your action could not be completed.");
			response.sendRedirect("/ecommerce/ManageJournal");
		}
		
	}
}

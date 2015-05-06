package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.sheffield.ecommerce.exceptions.*;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.Edition;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;
   
public class Seed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Seed.class.getName());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);
		try {
			createTestUser();
			httpSession.setAttribute("successMsg", "Seeding completed successfully");
		} catch (InvalidModelException | ConnectionProblemException ex) {
			httpSession.setAttribute("errorMsg", ex.getMessage());
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/login.jsp");
		requestDispatcher.forward(request, response);
	}
	

	private void createTestUser() throws InvalidModelException, ConnectionProblemException{
		//Start a database session
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		
		try {
			//Create a new user with seed data
			User user = new User();
			user.setEmail("john.doe@test.co.uk");
			user.setFirstName("john");
			user.setLastName("doe");
			user.setRole(1);
			PasswordHelper passwordHelper = new PasswordHelper("password");
			user.setPasswordHash(passwordHelper.getPasswordHash());
			user.setPasswordSalt(passwordHelper.getPasswordSalt());
			user.validateModel();
			
			Journal journal = new Journal();
			journal.setTitle("Test Journal Title");
			journal.setAcademicAims("Test content for academic aims");
			journal.setSubmissionGuidelines("Test content for submission guidelines");
			journal.validateModel();
			
			Volume volume = new Volume();
			volume.setPublicationDate(new Date());
			volume.setVolumeNumber(1);
			volume.validateModel();
			
			Edition edition = new Edition();
			edition.setPublicationDate(new Date());
			edition.setEditionNumber(1);
			edition.validateModel();

						
			//Save the user to the database
			session.beginTransaction();
			session.save(user);
			session.save(journal);
			
			volume.setJournal(journal);
			journal.getVolumes().add(volume);
			session.save(volume);
			
			edition.setVolume(volume);
			volume.getEditions().add(edition);			
			session.save(edition);
			
			session.getTransaction().commit();
			LOGGER.log(Level.FINE, "Created seed user");
			return;
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			throw ex;
		} catch (ConstraintViolationException ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
			session.getTransaction().rollback();
			throw new InvalidModelException("The seed data entered is invalid, please check and try again.");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			throw new ConnectionProblemException("A problem occurred and seeding could not be completed.");
		}
	}
}
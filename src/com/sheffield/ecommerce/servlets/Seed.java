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
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Edition;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;
   
public class Seed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Seed.class.getName());
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(true);
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
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		
		try {
			//Create a new user with seed data
			User editor = new User();
			editor.setEmail("editor@sheffield.ac.uk");
			editor.setFirstName("The");
			editor.setLastName("Editor");
			editor.setRole(1);
			PasswordHelper passwordHelper = new PasswordHelper("password");
			editor.setPasswordHash(passwordHelper.getPasswordHash());
			editor.setPasswordSalt(passwordHelper.getPasswordSalt());
			editor.validateModel();
			
			session.beginTransaction();
			session.save(editor);	
			session.getTransaction().commit();
			
			//Create a new user with seed data
			User reviewer = new User();
			reviewer.setEmail("reviewer@sheffield.ac.uk");
			reviewer.setFirstName("Mr");
			reviewer.setLastName("Reviewer");
			reviewer.setRole(0);
			passwordHelper = new PasswordHelper("password");
			reviewer.setPasswordHash(passwordHelper.getPasswordHash());
			reviewer.setPasswordSalt(passwordHelper.getPasswordSalt());
			reviewer.validateModel();
			
			session.beginTransaction();
			session.save(reviewer);	
			session.getTransaction().commit();
			
			//Create a new user with seed data
			User author = new User();
			author.setEmail("author@sheffield.ac.uk");
			author.setFirstName("An");
			author.setLastName("Author");
			author.setRole(0);
			passwordHelper = new PasswordHelper("password");
			author.setPasswordHash(passwordHelper.getPasswordHash());
			author.setPasswordSalt(passwordHelper.getPasswordSalt());
			author.validateModel();
			
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
			
			Article article = new Article();
			article.setTitle("Test Article Title");
			article.setArticle_abstract("Test article abstract");
			article.setFileName("TestFileName");
			article.validateModel();
						
			//Save the user to the database
			session.beginTransaction();
			session.save(author);
			session.save(journal);
			
			volume.setJournal(journal);
			journal.getVolumes().add(volume);
			session.save(volume);
			
			edition.setVolume(volume);
			volume.getEditions().add(edition);			
			session.save(edition);
			
			article.setAuthor(author);
			article.setEdition(edition);
			edition.getArticles().add(article);
			session.save(article);
			
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
		} finally {
			session.close();
		}
	}
}
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
import com.sheffield.ecommerce.models.Review;
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
			User author = new User();
			author.setEmail("author@sheffield.ac.uk");
			author.setFirstName("An");
			author.setLastName("Author");
			author.setRole(0);
			passwordHelper = new PasswordHelper("password");
			author.setPasswordHash(passwordHelper.getPasswordHash());
			author.setPasswordSalt(passwordHelper.getPasswordSalt());
			author.validateModel();
			
			User reviewer1 = new User();			
			reviewer1.setEmail("reviewer1@sheffield.ac.uk");
			reviewer1.setFirstName("Mr");
			reviewer1.setLastName("Reviewer1");
			reviewer1.setRole(0);
			passwordHelper = new PasswordHelper("password");
			reviewer1.setPasswordHash(passwordHelper.getPasswordHash());
			reviewer1.setPasswordSalt(passwordHelper.getPasswordSalt());
			reviewer1.validateModel();
			
			User reviewer2 = new User();
			reviewer2.setEmail("reviewer2@sheffield.ac.uk");
			reviewer2.setFirstName("Mr");
			reviewer2.setLastName("Reviewer2");
			reviewer2.setRole(0);
			passwordHelper = new PasswordHelper("password");
			reviewer2.setPasswordHash(passwordHelper.getPasswordHash());
			reviewer2.setPasswordSalt(passwordHelper.getPasswordSalt());
			reviewer2.validateModel();
			
			User reviewer3 = new User();
			reviewer3.setEmail("reviewer3@sheffield.ac.uk");
			reviewer3.setFirstName("Mr");
			reviewer3.setLastName("Reviewer3");
			reviewer3.setRole(0);
			passwordHelper = new PasswordHelper("password");
			reviewer3.setPasswordHash(passwordHelper.getPasswordHash());
			reviewer3.setPasswordSalt(passwordHelper.getPasswordSalt());
			reviewer3.validateModel();
			
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
			
			Article publishedArticle = new Article();
			publishedArticle.setTitle("Author's Published Article");
			publishedArticle.setArticle_abstract("Test article abstract");
			publishedArticle.setFileName("TestFileName");
			publishedArticle.validateModel();
			
			Article article = new Article();
			article.setTitle("Author's Article");
			article.setArticle_abstract("Test article abstract");
			article.setFileName("TestFileName");
			article.validateModel();
			
			Article article1 = new Article();
			article1.setTitle("Reviewer1's Article");
			article1.setArticle_abstract("Test article abstract");
			article1.setFileName("TestFileName");
			article1.validateModel();
			
			Article article2 = new Article();
			article2.setTitle("Reviewer2's Article");
			article2.setArticle_abstract("Test article abstract");
			article2.setFileName("TestFileName");
			article2.validateModel();
			
			Article article3 = new Article();
			article3.setTitle("Reviewer3's Article");
			article3.setArticle_abstract("Test article abstract");
			article3.setFileName("TestFileName");
			article3.validateModel();
								
			//Save the user to the database
			session.beginTransaction();
			session.save(author);
			session.save(reviewer1);
			session.save(reviewer2);
			session.save(reviewer3);
			session.save(journal);
			
			volume.setJournal(journal);
			journal.getVolumes().add(volume);
			session.save(volume);
			
			edition.setVolume(volume);
			volume.getEditions().add(edition);			
			session.save(edition);
			
			publishedArticle.setAuthor(author);
			publishedArticle.setEdition(edition);
			edition.getArticles().add(publishedArticle);
			session.save(publishedArticle);
			
			publishedArticle.setAuthor(author);
			session.save(publishedArticle);
			
			article.setAuthor(reviewer1);
			session.save(article);
			
			article1.setAuthor(reviewer1);
			session.save(article1);
			
			article2.setAuthor(reviewer2);
			session.save(article2);
			
			article3.setAuthor(reviewer3);
			session.save(article3);
						
			Review review1 = new Review();
			review1.setArticle(article1);
			review1.setOverallJudgement("champion");
			review1.setReviewerExpertise("reviewerExpertise");
			review1.setArticleSummary("articleSummary");
			review1.setSubstantiveCriticism("articleCriticism");
			review1.setSmallErrors("articleErrors");
			review1.setCommentsForEditor("secretComments");
			review1.setReviewer(author);
			review1.validateModel();
			session.save(review1);

			Review review2 = new Review();
			review2.setArticle(article2);
			review2.setOverallJudgement("champion");
			review2.setReviewerExpertise("reviewerExpertise");
			review2.setArticleSummary("articleSummary");
			review2.setSubstantiveCriticism("articleCriticism");
			review2.setSmallErrors("articleErrors");
			review2.setCommentsForEditor("secretComments");
			review2.setReviewer(author);
			review2.validateModel();
			session.save(review2);
			
			Review review3 = new Review();
			review3.setArticle(article3);
			review3.setOverallJudgement("champion");
			review3.setReviewerExpertise("reviewerExpertise");
			review3.setArticleSummary("articleSummary");
			review3.setSubstantiveCriticism("articleCriticism");
			review3.setSmallErrors("articleErrors");
			review3.setCommentsForEditor("secretComments");
			review3.setReviewer(author);
			review3.validateModel();
			session.save(review3);
			
			Review review4 = new Review();
			review4.setArticle(article);
			review4.setOverallJudgement("champion");
			review4.setReviewerExpertise("reviewerExpertise");
			review4.setArticleSummary("articleSummary");
			review4.setSubstantiveCriticism("articleCriticism");
			review4.setSmallErrors("articleErrors");
			review4.setCommentsForEditor("secretComments");
			review4.setReviewer(reviewer1);
			review4.validateModel();
			session.save(review4);
			
			Review review5 = new Review();
			review5.setArticle(article);
			review5.setOverallJudgement("champion");
			review5.setReviewerExpertise("reviewerExpertise");
			review5.setArticleSummary("articleSummary");
			review5.setSubstantiveCriticism("articleCriticism");
			review5.setSmallErrors("articleErrors");
			review5.setCommentsForEditor("secretComments");
			review5.setReviewer(reviewer2);
			review5.validateModel();
			session.save(review5);
			
			Review review6 = new Review();
			review6.setArticle(article);
			review6.setOverallJudgement("champion");
			review6.setReviewerExpertise("reviewerExpertise");
			review6.setArticleSummary("articleSummary");
			review6.setSubstantiveCriticism("articleCriticism");
			review6.setSmallErrors("articleErrors");
			review6.setCommentsForEditor("secretComments");
			review6.setReviewer(reviewer3);
			review6.validateModel();
			session.save(review6);
			
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
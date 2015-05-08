package com.sheffield.ecommerce.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.*;
import com.sheffield.ecommerce.helpers.PasswordHelper;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Edition;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;
import com.sheffield.ecommerce.models.Volume;

/**
 * This class is used to input some initial data into the system. 
 * 
 * 
 * ****	NOTE: This class is not intended to be used in a real production environment ******
 * 
 *  This class has been created purely for the purposes of demonstrating the system and as such may not conform to the structural and 'good coding' practices followed in the rest of the system 
 * 
 * 
 * 
 */
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
			journal.setTitle("The dissertations");
			journal.setAcademicAims("The journal aims to outline dissertations written by students from the Department of Computer Sciece at the University of Sheffield.");
			journal.setSubmissionGuidelines("Each dissertation must be between 30 and 60 pages and it must follow the department's guidelines on 3rd year dissertations.");
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
			publishedArticle.setTitle("An example paper");
			publishedArticle.setArticle_abstract("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			publishedArticle.setFileName("1431099295403.pdf");
			publishedArticle.validateModel();
			
			Article article = new Article();
			article.setTitle("Shadow Puppetry Using the Kinect");
			article.setArticle_abstract("Shadow puppetry is a form of storytelling where the characters are made from the shadows cast by puppets. The goal of this project is to build a real-time shadow puppet storytelling application using the Microsoft Kinect sensor. Its depth sensing ability is the perfect tool for tracking users and allowing them to control puppets onscreen, just by moving their body. The project is developed in the C++ programming language and uses the Cinder library for image processing. The system includes various image processing techniques to achieve a real-time, depth-based blur effect that is both efficient and visually appealing. It also includes a robust gesture recognition system based on the dynamic time warping algorithm to allow users to interact with the system and to create engaging and varied stories. These stories can be recorded and saved to file for later playback, or exported as video files.");
			article.setFileName("1431098947160.pdf");
			article.validateModel();
			
			Article article1 = new Article();
			article1.setTitle("D Portraiture System");
			article1.setArticle_abstract("The proliferation of 3D printers and their applications inevitably depend on the ease and practicality of 3D model construction. The aim of this project is to create a 3D portraiture system capable of capturing real-world facial data via a 360 degree head-scanning algorithm, processing this information and then reproducing a physical representation in the form of a figurine using custom built hardware from LEGO. The milling machine has been constructed and proven functional by producing several figurines, each with minor improvements over its predecessors. The implementation of the head scanning subsystem has also been successfully completed through the utilisation of a Microsoft â s Kinect for Windows, which generates partially overlapping point clouds that can subsequently be aligned and merged by means of the Iterative Closest Point algorithm. Future development and improvements to the program should be focused in this subsystem since many advanced techniques and enhancements exist that still remain unincorporated.");
			article1.setFileName("1431098976887.pdf");
			article1.validateModel();
			
			Article article2 = new Article();
			article2.setTitle("Robot Facial Expressions");
			article2.setArticle_abstract("The reason that made me choose this project is that I found it extremely interesting howpeople evolved regarding their interaction with machines. Obviously, as the machines became more and more advanced technologically, the human-computer interaction grew. I think that this growth has brought human kind to the point where we can start considering the possibility of emotions being expressed towards a robot, for example, and maybe in the future, from a robot towards a human being. The aim of this project is to take the first step towards that future by observing and studying how an animated man can be the target of our emotions or to see if the interaction with it can make us feel like we could haveemotions towards it.");
			article2.setFileName("1431099010458.pdf");
			article2.validateModel();
			
			Article article3 = new Article();
			article3.setTitle("Mobile application for prospective and current Computer Science Students");
			article3.setArticle_abstract("Nowadays, mobile phones are part of our life to the point where we are dependent on them. We rely on them for a wide variety of chores, from waking us up in the morning to keeping track of our gym routine. \n The University of Sheffield offers an application that gives access to its students to certain information; however, its contents are very general. That is why this project aims to offer more specific information and tools to the students of the Computer Science department. On top of that, the application also contains information for any prospective Computer Science students. \n The paper presents an overview of the current operating systems available on the market and then it looks into the applications that perform similar tasks. Using the data obtained through this research, the functional and non-functional requirements are presented and based on these the application is designed. Towards the end of the report, the techniques and algorithms that run the application are being described, along with the methods in which these have been tested. Finally, the report will end with a discussion based on the challenges faced by the project and an application evaluation. ");
			article3.setFileName("1431099026185.pdf");
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
			
			article.setAuthor(author);
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
			review1.setReviewerExpertise("outsider");
			review1.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review1.setSubstantiveCriticism("none");
			review1.setSmallErrors("none");
			review1.setCommentsForEditor("Very very good article. Can go to publishing");
			review1.setReviewer(author);
			review1.validateModel();
			session.save(review1);

			Review review2 = new Review();
			review2.setArticle(article2);
			review2.setOverallJudgement("detractor");
			review2.setReviewerExpertise("outsider");
			review2.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review2.setSubstantiveCriticism("none");
			review2.setSmallErrors("a few spelling mistakes");
			review2.setCommentsForEditor("good article!");
			review2.setReviewer(author);
			review2.validateModel();
			session.save(review2);
			
			Review review3 = new Review();
			review3.setArticle(article3);
			review3.setOverallJudgement("champion");
			review3.setReviewerExpertise("expert");
			review3.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review3.setSubstantiveCriticism("none");
			review3.setSmallErrors("none");
			review3.setCommentsForEditor("Good for publishing");
			review3.setReviewer(author);
			review3.validateModel();
			session.save(review3);
			
			Review review4 = new Review();
			review4.setArticle(article);
			review4.setOverallJudgement("champion");
			review4.setReviewerExpertise("expert");
			review4.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review4.setSubstantiveCriticism("none");
			review4.setSmallErrors("the indentation is messed up on page 5");
			review4.setCommentsForEditor("need revising but it's good overall");
			review4.setReviewer(reviewer1);
			review4.validateModel();
			session.save(review4);
			
			Review review5 = new Review();
			review5.setArticle(article);
			review5.setOverallJudgement("champion");
			review5.setReviewerExpertise("outsider");
			review5.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review5.setSubstantiveCriticism("none");
			review5.setSmallErrors("none");
			review5.setCommentsForEditor("perfect!");
			review5.setReviewer(reviewer2);
			review5.validateModel();
			session.save(review5);
			
			Review review6 = new Review();
			review6.setArticle(article);
			review6.setOverallJudgement("champion");
			review6.setReviewerExpertise("knowledgable");
			review6.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review6.setSubstantiveCriticism("none");
			review6.setSmallErrors("none");
			review6.setCommentsForEditor("good to go!");
			review6.setReviewer(reviewer3);
			review6.validateModel();
			session.save(review6);
			
			Review review7 = new Review();
			review7.setArticle(publishedArticle);
			review7.setOverallJudgement("champion");
			review7.setReviewerExpertise("expert");
			review7.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review7.setSubstantiveCriticism("None");
			review7.setSmallErrors("Spelling mistakes here and there.");
			review7.setCommentsForEditor("Very good article!");
			review7.setReviewer(reviewer1);
			review7.validateModel();
			session.save(review7);
			
			Review review8 = new Review();
			review8.setArticle(publishedArticle);
			review8.setOverallJudgement("champion");
			review8.setReviewerExpertise("knowledgable");
			review8.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review8.setSubstantiveCriticism("Not properly organized into sections. The article does not flow");
			review8.setSmallErrors("Gammar mistakes and spelling mistakes");
			review8.setCommentsForEditor("Good article but need revising");
			review8.setReviewer(reviewer2);
			review8.validateModel();
			session.save(review8);
			
			Review review9 = new Review();
			review9.setArticle(publishedArticle);
			review9.setOverallJudgement("champion");
			review9.setReviewerExpertise("outsider");
			review9.setArticleSummary("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \n Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \n It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			review9.setSubstantiveCriticism("None");
			review9.setSmallErrors("Losts of grammar code");
			review9.setCommentsForEditor("N/A");
			review9.setReviewer(reviewer3);
			review9.validateModel();
			session.save(review9);
			
			session.getTransaction().commit();
			session.close();
			
			UserDao.setArticleToReview(article1, author);
			UserDao.setArticleToReview(article2, author);
			UserDao.setArticleToReview(article3, author);
			UserDao.setArticleToReview(article, reviewer1);
			UserDao.setArticleToReview(article, reviewer2);
			UserDao.setArticleToReview(article, reviewer3);
			
			LOGGER.log(Level.FINE, "Created seed user");
			return;
		} catch (InvalidModelException ex) {
			//If there was any invalid User information then log and throw the message up to the user
			LOGGER.log(Level.INFO, ex.getMessage());
			session.close();
			throw ex;
		} catch (ConstraintViolationException ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getCause().getMessage());
			session.getTransaction().rollback();
			session.close();
			throw new InvalidModelException("The seed data entered is invalid, please check and try again.");
		} catch (Exception ex) {
			//If an unexpected error occurred then log, attempt to rollback and then throw a user friendly error
			LOGGER.log(Level.SEVERE, ex.getMessage());
			session.getTransaction().rollback();
			session.close();
			throw new ConnectionProblemException("A problem occurred and seeding could not be completed.");
		}
	}

}

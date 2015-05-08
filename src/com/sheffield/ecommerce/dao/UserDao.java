package com.sheffield.ecommerce.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

/**
 * Database Access Object which provides database manipulation methods for Users
 */
public class UserDao {

	/**
	 * Add a new user to the database
	 * @param user
	 * @throws InvalidModelException
	 */
	public static void addUser(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		user.validateModel();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Delete a user from the database
	 * @param id
	 */
	public static void deleteUser(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from User where id = :id");
		query.setParameter("id", id);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Update a user in the database, not including their password
	 * @param user
	 * @throws InvalidModelException
	 */
	public static void updateUser(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		user.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update User set first_name = :firstName, last_name = :lastName, email = :email, role = :role where id = :id");
		query.setParameter("id", user.getId());
		query.setParameter("firstName", user.getFirstName());
		query.setParameter("lastName", user.getLastName());
		query.setParameter("email", user.getEmail());
		query.setParameter("role", user.getRole());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Update a user in the database, including their password
	 * @param user
	 * @throws InvalidModelException
	 */
	public static void updateUserWithPassword(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		user.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update User set first_name = :firstName, last_name = :lastName, email = :email, role = :role, passwordHash = :passwordHash, passwordSalt = :passwordSalt where id = :id");
		query.setParameter("id", user.getId());
		query.setParameter("firstName", user.getFirstName());
		query.setParameter("lastName", user.getLastName());
		query.setParameter("email", user.getEmail());
		query.setParameter("role", user.getRole());
		query.setParameter("passwordHash", user.getPasswordHash());
		query.setParameter("passwordSalt", user.getPasswordSalt());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Fetch a list of all users in the database
	 * @return A list of users
	 */
	public static List<User> getAllUsers() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User");
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Fetch a specific user from the database by their id
	 * @param id
	 * @return The requested user
	 */
	public static User getUserById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.id = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}
	
	
	/**
	 * Fetch a specific user from the database by their email
	 * @param id
	 * @return The requested user
	 */
	public static User getUserByEmail(String email) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.email = :email");
		query.setMaxResults(1);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}
	
	/**
	 * Returns a list of articles that are awaiting a review from a user that has selected them 
	 * @param id User id 
	 * @return List of articles
	 */
	public static Set<Article> getArticlesToReview(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT u.articlesToReview FROM User u where u.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Article> articles = query.list();
		Set<Article> results = new HashSet<Article>(articles);
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Marks a given article as awaiting review by the given user
	 * @param article Article to be marked as awaiting review
	 * @param user User who is going to review it
	 */
	public static void setArticleToReview(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Set<Article> articles = getArticlesToReview(user.getId());
		articles.add(article);
		user.setArticlesToReview(articles);
		session.update(user);
		session.getTransaction().commit();
		session.close();	
	}
	
	/**
	 * Deletes the database connection that indicates an article is awaiting review from a user
	 * @param article
	 * @param user
	 */
	public static void deleteReviewedArticle(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Set<Article> articles = new HashSet<Article>();
		for(Article a : getArticlesToReview(user.getId())){
			if(a.getId() != article.getId())
				articles.add(a);	
		}
		user.setArticlesToReview(articles); 		
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Returns a count of the number of published articles for a given author
	 * @param userId The user whos articles should be considered
	 * @return The number of published articles
	 */
	public static int countUsersPublishedArticles(int userId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Article where author_id = :userId and edition_id is not null");
		query.setParameter("userId", userId);
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}
}

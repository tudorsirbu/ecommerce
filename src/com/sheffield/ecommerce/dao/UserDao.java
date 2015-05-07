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
 * 
 */
public class UserDao {

	/**
	 * Add a new user to the database
	 * @param user
	 * @throws InvalidModelException
	 */
	public void addUser(User user) throws InvalidModelException {
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
	public void deleteUser(int id) {
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
	public void updateUser(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		user.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update User set first_name = :firstName, last_name = :lastName, email = :email, role = :role where id = :id");
		query.setParameter("id", user.getId());
		query.setParameter("firstName", user.getFirstName());
		query.setParameter("lastName", user.getlastName());
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
	public void updateUserWithPassword(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		user.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update User set first_name = :firstName, last_name = :lastName, email = :email, role = :role, passwordHash = :passwordHash, passwordSalt = :passwordSalt where id = :id");
		query.setParameter("id", user.getId());
		query.setParameter("firstName", user.getFirstName());
		query.setParameter("lastName", user.getlastName());
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
	public List<User> getAllUsers() {
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
	public User getUserById(int id) {
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
	public User getUserByEmail(String email) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.email = :email");
		query.setMaxResults(1);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}
	
	public Set<Article> getArticlesToReview(int id) {
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
	
	public void setArticleToReview(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Set<Article> articles = getArticlesToReview(user.getId());
		articles.add(article);
		user.setArticlesToReview(articles);
		session.update(user);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void deleteReviewedArticle(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		user.deleteReviewedArticle(article);
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public int countUsersPublishedArticles(int userId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Article where author_id = :userId and edition_id != null");
		query.setParameter("userId", userId);
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}
}

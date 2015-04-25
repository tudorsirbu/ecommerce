package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
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
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		user.validateModel();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}
	
	/**
	 * Delete a user from the database
	 * @param id
	 */
	public void deleteUser(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from User where id = :id");
		query.setParameter("id", id);
		session.getTransaction().commit();
	}
	
	/**
	 * Update a user in the database, not including their password
	 * @param user
	 * @throws InvalidModelException
	 */
	public void updateUser(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
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
	}
	
	/**
	 * Update a user in the database, including their password
	 * @param user
	 * @throws InvalidModelException
	 */
	public void updateUserWithPassword(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
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
	}
	
	/**
	 * Fetch a list of all users in the database
	 * @return A list of users
	 */
	public List<User> getAllUsers() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User");
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		return results;
	}
	
	/**
	 * Fetch a specific user from the database by their id
	 * @param id
	 * @return The requested user
	 */
	public User getUserById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.id = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	
	/**
	 * Fetch a specific user from the database by their email
	 * @param id
	 * @return The requested user
	 */
	public User getUserByEmail(String email) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.email = :email");
		query.setMaxResults(1);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}
}

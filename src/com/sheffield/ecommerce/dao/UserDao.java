package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

public class UserDao {

	public void addUser(User user) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		user.validateModel();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}
	
	public void deleteUser(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from User where id = :id");
		query.setParameter("id", id);
		session.getTransaction().commit();
	}
	
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
	
	public List<User> getAllUsers() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User");
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		return results;
	}
	
	public User getUserById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User u where u.id = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		return user;
	}
}

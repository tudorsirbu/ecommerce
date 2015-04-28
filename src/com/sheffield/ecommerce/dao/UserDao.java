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
	
	public Set<Article> getArticlesToReview(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT u.articlesToReview FROM User u where u.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Article> articles = query.list();
		Set<Article> results = new HashSet<Article>(articles);
		session.getTransaction().commit();
		return results;
	}
	
	public void setArticleToReview(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Set<Article> articles = getArticlesToReview(user.getId());
		articles.add(article);
		user.setArticlesToReview(articles);
		session.update(user);
		session.getTransaction().commit();
		
	}
	
	public void deleteReviewedArticle(Article article, User user) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		user.deleteReviewedArticle(article);
		session.update(user);
		session.getTransaction().commit();
		
	}
}

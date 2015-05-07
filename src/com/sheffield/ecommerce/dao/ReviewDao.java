package com.sheffield.ecommerce.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

public class ReviewDao {

	/**
	 * Fetch a list of reviews given an article id
	 * @param id
	 * @return The list of reviews
	 */
	public List<Review> getReviewsForArticle(int article_id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select r from Review as r left join r.article as a where a.id = :article_id");
		query.setParameter("article_id", article_id);
		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	public boolean isUserReviewingArticle(User user, int article_id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where (a.id = :article_id and a.author <> :user and :user member of a.reviewers)");
		query.setParameter("user", user); 
		query.setParameter("article_id", article_id);
		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		session.close();
		return !results.isEmpty();
	}
	
	public void addReview(Review review) throws InvalidModelException {
		review.validateModel();
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(review);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Review> getReviewsForUserAndArticle(User user, Article article) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select r from Review as r left join r.article as a left join r.reviewer as u where (a.id = :article_id and u.id = :user_id)");
		query.setParameter("user_id", user.getId()); 
		query.setParameter("article_id", article.getId());
		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	public List<Review> getReviewsForUser(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Review r where r.reviewer = :user");
		query.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}

	public int countReviewsForUser(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Review where user_id = :userId");
		query.setParameter("userId", user.getId()); 
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}
	
	public List<Review> getThreeMostRecentReviews(int articleId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select r from Review as r left join r.article as a where a.id = :article_id order by review_id DESC");
		query.setParameter("article_id", articleId);
		query.setMaxResults(3);

		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
}

package com.sheffield.ecommerce.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.User;

/**
 * This class abstracts data access methods relating to reviews into a single file
 */
public class ReviewDao {

	/**
	 * Fetch a list of reviews given an article id
	 * @param id
	 * @return The list of reviews
	 */
	public static List<Review> getReviewsForArticle(int article_id) {
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
	
	/**
	 * Given an article and a user, returns true if the article has been reviewed by the user
	 * @param user
	 * @param article_id
	 * @return
	 */
	public static boolean isUserReviewingArticle(User user, int article_id) {
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
	
	/**
	 * Adds the given review to the database
	 * @param review
	 * @throws InvalidModelException
	 */
	public static void addReview(Review review) throws InvalidModelException {
		review.validateModel();
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(review);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Returns a list of reviews made by a user about a given article
	 * @param user
	 * @param article
	 * @return
	 */
	public static List<Review> getReviewsForUserAndArticle(User user, Article article) {
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
	
	/**
	 * Returns a list of all reviews made by a given user
	 * @param user
	 * @return
	 */
	public static List<Review> getReviewsForUser(User user) {
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

	/**
	 * Counts the number of reviews made by a given user
	 * @param user
	 * @return
	 */
	public static int countReviewsForUser(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Review where user_id = :userId");
		query.setParameter("userId", user.getId()); 
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}
	
	/**
	 * Returns a list of the three most recent reviews made about an article (this will in essence cause reviews made about earlier revisions to be ignored)
	 * @param articleId
	 * @return
	 */
	public static List<Review> getThreeMostRecentReviews(int articleId) {
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
	
	/**
	 * Deletes a review with the given id
	 * @param review_id
	 */
	public static void deleteReview(int review_id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete Review r where r.id = :review_id");
		query.setParameter("review_id", review_id);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Returns a review with the givne id
	 * @param id
	 * @return
	 */
	public static Review getReviewById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Review r where r.id = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Review review = (Review) query.uniqueResult();
		Hibernate.initialize(review.getReviewer());
		Hibernate.initialize(review.getArticle());
		session.close();
		return review;
	}
	
}

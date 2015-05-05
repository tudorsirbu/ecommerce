package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.models.Review;
import com.sheffield.ecommerce.models.SessionFactoryUtil;

public class ReviewDao {

	/**
	 * Fetch a list of reviews given an article id
	 * @param id
	 * @return The requested user
	 */
	public List<Review> getReviewsForArticle(int article_id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Review r where r.article_id = :article_id");
		@SuppressWarnings("unchecked")
		List<Review> results = query.list();
		session.getTransaction().commit();
		return results;
	}
	
}

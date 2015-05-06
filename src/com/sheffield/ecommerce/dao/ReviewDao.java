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
	
}

package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class ArticleDao {

	public static void addArticle(Article article) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		article.validateModel();
		session.beginTransaction();
		session.save(article);
		session.getTransaction().commit();
	}
	
	public static List<Article> getArticlesForUser(int userId) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where a.main_contact_id = :id");
		query.setParameter("id", userId);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		return results;
	}
}

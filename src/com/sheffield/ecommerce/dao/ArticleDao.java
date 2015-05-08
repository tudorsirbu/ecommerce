package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

/**
 * This class abstracts data access methods relating to Articles into a single file
 */
public class ArticleDao {

	/**
	 * Adds a given article to the database
	 * @param article
	 * @throws InvalidModelException
	 */
	public static void addArticle(Article article) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		article.validateModel();
		session.beginTransaction();
		session.save(article);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Returns a list of articles uploaded by a given user
	 * @param author
	 * @return
	 */
	public static List<Article> getArticlesForUser(User author) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where a.author = :author");
		query.setParameter("author", author);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Returns a list of articles with the passed title
	 * @param title
	 * @return
	 */
	public static List<Article> getArticlesWithTitle(String title) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where a.title LIKE :title");
		query.setParameter("title", "%" + title + "%");
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Gets the 10 latest articles that require review ordered by the oldest first
	 * @param user The current logged in user
	 */
	public static List<Article> getArticlesForReview(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query;
		if(user.getRole() == User.EDITOR)
			query = session.createQuery("from Article a where a.author <> :user and a.edition is null order by a.id asc");
		else
			query = session.createQuery("from Article a where (a.author <> :user and a.edition is null and :user not member of a.reviewers) order by a.id asc");
		query.setParameter("user", user);
		query.setMaxResults(10);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Returns a list of articles being reviewed by the given user
	 * @param user
	 * @return
	 */
	public static List<Article> getArticlesBeingReviewed(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query;
		query = session.createQuery("from Article a where (a.author <> :user and :user member of a.reviewers)");
		query.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Returns true if the given user is the author of the article with the passed it
	 * @param articleId
	 * @param author
	 * @return
	 */
	public static boolean doesArticleBelongToUser(int articleId, User author){
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where a.id = :id AND a.author = :author");
		query.setMaxResults(1);
		query.setParameter("id", articleId);
		query.setParameter("author", author);
		Article article = (Article) query.uniqueResult();
		session.close();
		return article != null ? true : false;
	}
	
	/**
	 * Returns an article with the given id
	 * @param id
	 * @return
	 */
	public static Article getArticleById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a where a.id = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Article article = (Article) query.uniqueResult();
		Hibernate.initialize(article.getAuthor());
		session.close();
		return article;
	}

	/**
	 * Updates an article in the database with the given article object which should 
	 * @param article
	 * @throws InvalidModelException
	 */
	public static void reviseArticle(Article article) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		article.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Article set fileNameRevision1 = :fileNameRevision1, revisionDetails1 = :revisionDetails1 where id = :id");
		query.setParameter("id", article.getId());
		query.setParameter("fileNameRevision1", article.getFileNameRevision1());
		query.setParameter("revisionDetails1", article.getRevisionDetails1());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Updates an article in the database with the given article object which should reject the article
	 * @param article
	 */
	public static void rejectRevision(Article article) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("update Article set fileNameRevision1 = :fileNameRevision1, revisionDetails1 = :revisionDetails1 where id = :id");
		query.setParameter("id", article.getId());
		query.setParameter("fileNameRevision1", null);
		query.setParameter("revisionDetails1", null);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Return a list of users who are reviewing an article with the passed id
	 * @param id Article id
	 * @return
	 */
	public static List<User> getReviewers(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT a.reviewers FROM Article a where a.id = :id");
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<User> result = query.list();
		session.getTransaction().commit();
		session.close();
		return result;
	}

	/**
	 * Returns a list of all the unpublished articles
	 * @return
	 */
	public static List<Article> getAllUnpublishedArticles() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article where edition_id = null");
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	/**
	 * Returns a list of all the published articles
	 * @return
	 */
	public static List<Article> getAllPublishedArticles() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article where edition_id != null");
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
}

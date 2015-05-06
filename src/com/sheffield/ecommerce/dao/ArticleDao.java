package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.User;

public class ArticleDao {

	public static void addArticle(Article article) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		article.validateModel();
		session.beginTransaction();
		session.save(article);
		session.getTransaction().commit();
		session.close();
	}
	
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
	
	public static List<Article> getAllArticles() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article a");
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
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
	
	
	public static List<Article> getArticlesForReview(User user) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query;
		if(user.getRole() == User.EDITOR)
			query = session.createQuery("from Article a where a.author <> :user");
		else
			query = session.createQuery("from Article a where (a.author <> :user and :user not member of a.reviewers)");
		query.setParameter("user", user);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
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

	public static void reviseArticle(Article article) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		article.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Article set title = :title, fileNameRevision1 = :fileNameRevision1, revisionDetails1 = :revisionDetails1 where id = :id");
		query.setParameter("id", article.getId());
		query.setParameter("fileNameRevision1", article.getFileNameRevision1());
		query.setParameter("title", article.getTitle());
		query.setParameter("revisionDetails1", article.getRevisionDetails1());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
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
}

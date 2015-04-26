package com.sheffield.ecommerce.models;

import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Article implements Serializable {
	private static final long serialVersionUID = 6924059181624616239L;
	int id;
	String title;
	String article_abstract;
	int main_contact_id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticle_abstract() {
		return article_abstract;
	}

	public void setArticle_abstract(String article_abstract) {
		this.article_abstract = article_abstract;
	}

	public int getMain_contact_id() {
		return main_contact_id;
	}

	public void setMain_contact_id(int main_contact_id) {
		this.main_contact_id = main_contact_id;
	}

	//TODO Complete validation for model
	public void validateModel() throws InvalidModelException {
//		if (firstName == null || firstName.isEmpty()){
//			throw new InvalidModelException("First name cannot be empty.");
//		}
//		if (lastName == null || lastName.isEmpty()){
//			throw new InvalidModelException("Surname cannot be empty.");
//		}
//		if (password == null || password.isEmpty()){
//			throw new InvalidModelException("Password cannot be empty.");
//		}
//		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		Query query = session.createQuery("SELECT 1 FROM User u where u.email = :email");
//		query.setParameter("email", email);
//		if (query.uniqueResult() != null) { 
//			throw new InvalidModelException("This email has already been used.");
//		}
	}
	
	
}

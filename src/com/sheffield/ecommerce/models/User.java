package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.dao.UserDao;
import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class User implements Serializable {
	private static final long serialVersionUID = 5999500982028327711L;
	public static final int AUTHOR = 0;
	public static final int EDITOR = 1;
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;
	private String passwordSalt;
	private int role;
	private Set<Article> articlesToReview = new HashSet<Article>();
	private Set<Review> reviews = new HashSet<Review>();
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public void deleteReviewedArticle(Article article){
		Set<Article> articles = new HashSet<Article>();
		UserDao dao = new UserDao();
		for(Article a : dao.getArticlesToReview(id)){
			if(a.getId() != article.getId())
				articles.add(a);	
		}
		setArticlesToReview(articles);
		    
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public int getRole() {
		return role;
	}
	
	public String getRoleName() {
		return (role == 1) ? "Editor" : "Author/Reviewer";
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getlastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
		
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) throws InvalidModelException {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) throws InvalidModelException {
		this.lastName = lastName;
	}

	public void setEmail(String email) throws InvalidModelException {
		this.email = email;
	}
	
	public boolean equals(Object obj) {
	      if (obj == null) return false;
	      if (!this.getClass().equals(obj.getClass())) return false;

	      User obj2 = (User)obj;
	      if(this.id == obj2.getId())
	      {
	         return true;
	      }
	      return false;
	   }
	
	public void validateModel() throws InvalidModelException {
		// Check the first name is present
		if (firstName == null || firstName.isEmpty()){
			throw new InvalidModelException("First name cannot be empty.");
		}
		// Check the last name is present
		if (lastName == null || lastName.isEmpty()){
			throw new InvalidModelException("Surname cannot be empty.");
		}
		// Open a new database connection
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		// Check that the email is unique if creating
		Query query = session.createQuery("SELECT 1 FROM User u where u.email = :email AND u.id <> :id");
		query.setParameter("email", email);
		query.setParameter("id", id);
		if (query.uniqueResult() != null) { 
			session.getTransaction().commit();
			throw new InvalidModelException("This email has already been used.");
		}
		
		// Check that there is at least one editor in the database after this update
		if (role != EDITOR) {
			Query editorQuery = session.createQuery("SELECT 1 FROM User u where u.role = :role AND u.id <> :id");
			editorQuery.setParameter("role", EDITOR);
			editorQuery.setParameter("id", id);
			if (editorQuery.uniqueResult() == null) { 
				session.getTransaction().commit();
				throw new InvalidModelException("There must be at least one editor.");
			}
		}
		// Commit the transaction and close the connection
		session.getTransaction().commit();
	}

	public Set<Article> getArticlesToReview() {
		return articlesToReview;
	}

	public void setArticlesToReview(Set<Article> articlesToReview) {
		this.articlesToReview = articlesToReview;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
}

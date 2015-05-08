package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
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
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the passwordSalt
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}

	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(int role) {
		this.role = role;
	}

	/**
	 * @return the articlesToReview
	 */
	public Set<Article> getArticlesToReview() {
		return articlesToReview;
	}

	/**
	 * @param articlesToReview the articlesToReview to set
	 */
	public void setArticlesToReview(Set<Article> articlesToReview) {
		this.articlesToReview = articlesToReview;
	}

	/**
	 * @return the reviews
	 */
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return The string representaiton of the role
	 */
	public String getRoleName() {
		return (role == 1) ? "Editor" : "Author/Reviewer";
	}

	/**
	 * compares two instances
	 */
	public boolean equals(Object obj) {
      if (obj == null){
    	  return false;
      }
      if (!this.getClass().equals(obj.getClass())){
    	  return false;
      }
      User obj2 = (User)obj;
      if(this.id == obj2.getId()) {
         return true;
      }
      return false;
	}
	
	/**
	 * This method will check that the model conforms to validation rules. If it does not an exception is thrown with the details
	 * @throws InvalidModelException Contains the validation rule that was broken
	 */
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
	
}

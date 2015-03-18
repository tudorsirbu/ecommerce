package com.sheffield.ecommerce.models;

import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;
import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class User implements Serializable {
	private static final long serialVersionUID = 5999500982028327711L;
	int id;
	String firstName;
	String lastName;
	String email;
	String password;
	
	
	
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
	
	public String getPassword() {
		return password;
	}
	
	//TODO Complete validation in setters:
	
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) throws InvalidModelException {
		if (firstName == null || firstName.isEmpty()){
			throw new InvalidModelException("First name cannot be empty.");
		}
		this.firstName = firstName;
	}

	public void setLastName(String lastName) throws InvalidModelException {
		if (lastName == null || lastName.isEmpty()){
			throw new InvalidModelException("Surname name cannot be empty.");
		}
		this.lastName = lastName;
	}

	public void setEmail(String email) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT 1 FROM User u where u.email = :email");
		query.setParameter("email", email);
		if (query.uniqueResult() != null) { 
			throw new InvalidModelException("This email has already been used.");
		}
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPassword(String password, String passwordConfirmation) throws InvalidModelException {
		if (!password.equals(passwordConfirmation)){
			throw new InvalidModelException("Passwords do not match.");
		}
		setPassword(password);
	}
	
}

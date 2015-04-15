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
	int role;
	
	
	
	public int getRole() {
		return role;
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
	
	public String getPassword() {
		return password;
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

	public void setPassword(String password) {
		this.password = password;
	}
	
	//TODO Complete validation for model
	public void validateModel() throws InvalidModelException {
		if (firstName == null || firstName.isEmpty()){
			throw new InvalidModelException("First name cannot be empty.");
		}
		if (lastName == null || lastName.isEmpty()){
			throw new InvalidModelException("Surname cannot be empty.");
		}
		if (password == null || password.isEmpty()){
			throw new InvalidModelException("Password cannot be empty.");
		}
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT 1 FROM User u where u.email = :email");
		query.setParameter("email", email);
		if (query.uniqueResult() != null) { 
			throw new InvalidModelException("This email has already been used.");
		}
	}
	
	
}

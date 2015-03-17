package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.List;

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
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getlastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "SELECT * FROM User u WHERE u.email = :email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		session.getTransaction().commit();
		if (results.size() > 0) {
			throw new InvalidModelException("Error, this email has already been used.");
		}
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void validate() throws InvalidModelException{
		if (firstName == null || firstName.isEmpty()){
			throw new InvalidModelException("Error, First name cannot be empty.");
		}
	}

}

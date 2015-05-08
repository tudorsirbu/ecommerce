package com.sheffield.ecommerce.helpers;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * This file extends the authenticator class and is used to authenticate a user with the email client
 */
public class GMailAuthenticator extends Authenticator {
	String user;
	String pw;
	
	public GMailAuthenticator (String username, String password) {
	   super();
	   this.user = username;
	   this.pw = password;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
	      return new PasswordAuthentication(user, pw);
	}
}

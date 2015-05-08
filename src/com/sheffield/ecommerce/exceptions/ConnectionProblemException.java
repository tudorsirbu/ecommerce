package com.sheffield.ecommerce.exceptions;

/**
 * This class extends the exception class and identifies errors thrown as a result of a database connection problem
 */
public class ConnectionProblemException extends Exception{
	private static final long serialVersionUID = -6557078729643360570L;
	public ConnectionProblemException(String message){
		super(message);
	}
}
package com.sheffield.ecommerce.exceptions;

public class ConnectionProblemException extends Exception{
	private static final long serialVersionUID = -6557078729643360570L;
	public ConnectionProblemException(String message){
		super(message);
	}
}
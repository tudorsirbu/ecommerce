package com.sheffield.ecommerce.exceptions;

/**
 * This class extends the exception class and identifies errors thrown as a result of a model not conforming to validation conditions
 */
public class InvalidModelException extends Exception{
	private static final long serialVersionUID = -3058257500543497111L;

	public InvalidModelException(String message){
		super(message);
	}
}
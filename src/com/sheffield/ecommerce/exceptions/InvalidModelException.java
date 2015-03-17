package com.sheffield.ecommerce.exceptions;

public class InvalidModelException extends Exception{
	private static final long serialVersionUID = -3058257500543497111L;

	public InvalidModelException(String message){
		super(message);
	}
}
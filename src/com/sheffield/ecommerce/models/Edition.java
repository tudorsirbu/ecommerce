package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.Date;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Edition implements Serializable{
	private static final long serialVersionUID = 7969482339199686289L;
	private int editionId;
	private Volume volume;
	private int editionNumber = -1;
	private Date publicationDate;
	
	public int getEditionId() {
		return editionId;
	}

	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}

	public Volume getVolume() {
		return volume;
	}
	
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public int getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public void validateModel() throws InvalidModelException {
		if (editionNumber < 1){
			throw new InvalidModelException("Invalid edition number.");
		}
		if (publicationDate == null){
			throw new InvalidModelException("Edition must have a publication date.");
		}
	}
}

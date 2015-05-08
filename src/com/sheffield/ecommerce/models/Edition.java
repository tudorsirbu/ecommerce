package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Edition implements Serializable{
	private static final long serialVersionUID = 7969482339199686289L;
	private int editionId;
	private Volume volume;
	private int editionNumber = -1;
	private Date publicationDate;
	private Set<Article> articles = new HashSet<Article>(0);
		
	/**
	 * @return the editionId
	 */
	public int getEditionId() {
		return editionId;
	}

	/**
	 * @param editionId the editionId to set
	 */
	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}

	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	/**
	 * @return the editionNumber
	 */
	public int getEditionNumber() {
		return editionNumber;
	}

	/**
	 * @param editionNumber the editionNumber to set
	 */
	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}

	/**
	 * @return the publicationDate
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @return the articles
	 */
	public Set<Article> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * This method will check that the model conforms to validation rules. If it does not an execption is thrown with the details
	 * @throws InvalidModelException Contains the validation rule that was broken
	 */
	public void validateModel() throws InvalidModelException {
		if (editionNumber < 1){
			throw new InvalidModelException("Invalid edition number.");
		}
		if (publicationDate == null){
			throw new InvalidModelException("Edition must have a publication date.");
		}
	}
}

package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Volume implements Serializable{
	private static final long serialVersionUID = 605522379963153188L;
	private int volumeId;
	private Journal journal;
	private int volumeNumber = -1;
	private Date publicationDate;
	private Set<Edition> editions = new HashSet<Edition>(0);
		
	/**
	 * @return the volumeId
	 */
	public int getVolumeId() {
		return volumeId;
	}

	/**
	 * @param volumeId the volumeId to set
	 */
	public void setVolumeId(int volumeId) {
		this.volumeId = volumeId;
	}

	/**
	 * @return the journal
	 */
	public Journal getJournal() {
		return journal;
	}

	/**
	 * @param journal the journal to set
	 */
	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	/**
	 * @return the volumeNumber
	 */
	public int getVolumeNumber() {
		return volumeNumber;
	}

	/**
	 * @param volumeNumber the volumeNumber to set
	 */
	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
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
	 * @return the editions
	 */
	public Set<Edition> getEditions() {
		return editions;
	}

	/**
	 * @param editions the editions to set
	 */
	public void setEditions(Set<Edition> editions) {
		this.editions = editions;
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
		if (volumeNumber < 1){
			throw new InvalidModelException("Invalid volume number.");
		}
		if (publicationDate == null){
			throw new InvalidModelException("Volume must have a publication date.");
		}
	}
}

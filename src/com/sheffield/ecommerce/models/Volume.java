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
	
	public int getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(int volumeId) {
		this.volumeId = volumeId;
	}

	public Journal getJournal() {
		return journal;
	}
	
	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Set<Edition> getEditions() {
		return editions;
	}

	public void setEditions(Set<Edition> editions) {
		this.editions = editions;
	}

	public int getVolumeNumber() {
		return volumeNumber;
	}

	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

	public void setPublicationDate(Date date) {
		this.publicationDate = date;
	}

	public Object getPublicationDate() {
		return this.publicationDate;
	}
	
	public void validateModel() throws InvalidModelException {
		if (volumeNumber < 1){
			throw new InvalidModelException("Invalid volume number.");
		}
		if (publicationDate == null){
			throw new InvalidModelException("Volume must have a publication date.");
		}
	}
}

package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Volume implements Serializable{
	private static final long serialVersionUID = 605522379963153188L;
	private int volumeId;
	private Journal journal;
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
}

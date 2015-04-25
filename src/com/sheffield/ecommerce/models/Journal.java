package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Journal implements Serializable {
	private static final long serialVersionUID = 163831745344570776L;
	private int journalId;
	private String title;
	private String academicAims;
	private Set<Volume> volumes = new HashSet<Volume>(0);

	public int getJournalId() {
		return journalId;
	}

	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAcademicAims() {
		return academicAims;
	}
	
	public void setAcademicAims(String academicAims) {
		this.academicAims = academicAims;
	}

	public Set<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(Set<Volume> volumes) {
		this.volumes = volumes;
	}

	public void validateModel() throws InvalidModelException {
		// TODO validate this model
	}
}

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
	private String submissionGuidelines;
	private Set<Volume> volumes = new HashSet<Volume>(0);

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the journalId
	 */
	public int getJournalId() {
		return journalId;
	}

	/**
	 * @param journalId the journalId to set
	 */
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the academicAims
	 */
	public String getAcademicAims() {
		return academicAims;
	}

	/**
	 * @param academicAims the academicAims to set
	 */
	public void setAcademicAims(String academicAims) {
		this.academicAims = academicAims;
	}

	/**
	 * @return the submissionGuidelines
	 */
	public String getSubmissionGuidelines() {
		return submissionGuidelines;
	}

	/**
	 * @param submissionGuidelines the submissionGuidelines to set
	 */
	public void setSubmissionGuidelines(String submissionGuidelines) {
		this.submissionGuidelines = submissionGuidelines;
	}

	/**
	 * @return the volumes
	 */
	public Set<Volume> getVolumes() {
		return volumes;
	}

	/**
	 * @param volumes the volumes to set
	 */
	public void setVolumes(Set<Volume> volumes) {
		this.volumes = volumes;
	}

	/**
	 * This method will check that the model conforms to validation rules. If it does not an execption is thrown with the details
	 * @throws InvalidModelException Contains the validation rule that was broken
	 */
	public void validateModel() throws InvalidModelException {
		if (title == null || title.isEmpty()){
			throw new InvalidModelException("Journal title cannot be empty.");
		}
		if (academicAims == null || academicAims.isEmpty()){
			throw new InvalidModelException("Academic aims cannot be empty.");
		}
		if (submissionGuidelines == null || submissionGuidelines.isEmpty()){
			throw new InvalidModelException("Submission guidelines cannot be empty.");
		}
		if (academicAims.length() > 500){
			throw new InvalidModelException("Academic aims must be less than 500 characters.");
		}
		if (submissionGuidelines.length() > 500){
			throw new InvalidModelException("Submission guidelines must be less than 500 characters.");
		}
	}
}

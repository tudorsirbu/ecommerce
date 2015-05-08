package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Article implements Serializable {
	private static final long serialVersionUID = 6924059181624616239L;
	private Set<User> reviewers = new HashSet<User>(0);
	private int id;
	private String title;
	private Edition edition;
	private String article_abstract;
	private String fileName;
	private String fileNameRevision1;
	private String revisionDetails1;
	private String otherAuthors;
	private User author;
	private Set<Review> reviews = new HashSet<Review>(0);
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the reviewers
	 */
	public Set<User> getReviewers() {
		return reviewers;
	}

	/**
	 * @param reviewers the reviewers to set
	 */
	public void setReviewers(Set<User> reviewers) {
		this.reviewers = reviewers;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the edition
	 */
	public Edition getEdition() {
		return edition;
	}

	/**
	 * @param edition the edition to set
	 */
	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	/**
	 * @return the article_abstract
	 */
	public String getArticle_abstract() {
		return article_abstract;
	}

	/**
	 * @param article_abstract the article_abstract to set
	 */
	public void setArticle_abstract(String article_abstract) {
		this.article_abstract = article_abstract;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileNameRevision1
	 */
	public String getFileNameRevision1() {
		return fileNameRevision1;
	}

	/**
	 * @param fileNameRevision1 the fileNameRevision1 to set
	 */
	public void setFileNameRevision1(String fileNameRevision1) {
		this.fileNameRevision1 = fileNameRevision1;
	}

	/**
	 * @return the revisionDetails1
	 */
	public String getRevisionDetails1() {
		return revisionDetails1;
	}

	/**
	 * @param revisionDetails1 the revisionDetails1 to set
	 */
	public void setRevisionDetails1(String revisionDetails1) {
		this.revisionDetails1 = revisionDetails1;
	}

	/**
	 * @return the otherAuthors
	 */
	public String getOtherAuthors() {
		return otherAuthors;
	}

	/**
	 * @param otherAuthors the otherAuthors to set
	 */
	public void setOtherAuthors(String otherAuthors) {
		this.otherAuthors = otherAuthors;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return the reviews
	 */
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * Compares two instances
	 */
	public boolean equals(Object obj) {
	      if (obj == null) return false;
	      if (!this.getClass().equals(obj.getClass())) return false;

	      Article obj2 = (Article)obj;
	      if(this.id == obj2.getId())
	      {
	         return true;
	      }
	      return false;
	   }

	/**
	 * Returns the file name of the latest revision
	 * @return The file name as a string
	 */
	public String getLatestFileName() {
		if (this.fileNameRevision1 != null && !this.fileNameRevision1.equals("")) {
			return this.fileNameRevision1;
		} else {
			return this.fileName;
		}
	}

	/**
	 * This method will check that the model conforms to validation rules. If it does not an execption is thrown with the details
	 * @throws InvalidModelException Contains the validation rule that was broken
	 */
	public void validateModel() throws InvalidModelException {
		if (title == null || title.isEmpty()){
			throw new InvalidModelException("Article title cannot be empty.");
		}
		if (article_abstract == null || article_abstract.isEmpty()){
			throw new InvalidModelException("Article abstract cannot be empty.");
		}
		if (fileName == null || fileName.isEmpty()){
			throw new InvalidModelException("Article attachment cannot be missing.");
		}
	}

	/**
	 * Returns the number of revisions made to this article
	 * @return The number of revisions
	 */
	public int getNumberOfRevisions() {
		int revisions = 0;
		if (this.fileNameRevision1 != null && !this.fileNameRevision1.equals("")) { revisions++; }
		return revisions;
	}
	
}

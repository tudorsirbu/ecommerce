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
	private User author;
	private Set<Review> reviews = new HashSet<Review>(0);
	
	public Set<User> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<User> reviewers) {
		this.reviewers = reviewers;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	public String getArticle_abstract() {
		return article_abstract;
	}

	public void setArticle_abstract(String article_abstract) {
		this.article_abstract = article_abstract;
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
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

	public String getFileNameRevision1() {
		return fileNameRevision1;
	}

	public void setFileNameRevision1(String fileNameRevision1) {
		this.fileNameRevision1 = fileNameRevision1;
	}
	
	public String getRevisionDetails1() {
		return revisionDetails1;
	}

	public void setRevisionDetails1(String revisionDetails1) {
		this.revisionDetails1 = revisionDetails1;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
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
	
    public int hashCode() {
        return this.id;
    }
}

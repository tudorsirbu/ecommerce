package com.sheffield.ecommerce.models;

import java.io.Serializable;
import java.util.*;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Article implements Serializable {
	private static final long serialVersionUID = 6924059181624616239L;
	private int id;
	private String title;
	private String article_abstract;
	private String fileName;
	private String fileNameRevision1;
	private String fileNameRevision2;
	private String revisionDetails1;
	private String revisionDetails2;
	private User author;
	private Set<Review> reviews = new HashSet<Review>(0);
	
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
	
	public String getFileNameRevision1() {
		return fileNameRevision1;
	}

	public void setFileNameRevision1(String fileNameRevision1) {
		this.fileNameRevision1 = fileNameRevision1;
	}

	public String getFileNameRevision2() {
		return fileNameRevision2;
	}

	public void setFileNameRevision2(String fileNameRevision2) {
		this.fileNameRevision2 = fileNameRevision2;
	}
	
	public String getRevisionDetails1() {
		return revisionDetails1;
	}

	public void setRevisionDetails1(String revisionDetails1) {
		this.revisionDetails1 = revisionDetails1;
	}

	public String getRevisionDetails2() {
		return revisionDetails2;
	}

	public void setRevisionDetails2(String revisionDetails2) {
		this.revisionDetails2 = revisionDetails2;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	public String getLatestFileName() {
		if (this.fileNameRevision2 != null && !this.fileNameRevision2.equals("")) {
			return this.fileNameRevision2;
		} else if (this.fileNameRevision1 != null && !this.fileNameRevision1.equals("")) {
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
	
	public int getNumberOfRevisions() {
		int revisions = 0;
		if (this.fileNameRevision1 != null && !this.fileNameRevision1.equals("")) { revisions++; }
		if (this.fileNameRevision2 != null && !this.fileNameRevision2.equals("")) { revisions++; }
		return revisions;
	}
	
}

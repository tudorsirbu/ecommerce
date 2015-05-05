package com.sheffield.ecommerce.models;

import java.io.Serializable;

import com.sheffield.ecommerce.exceptions.InvalidModelException;

public class Article implements Serializable {
	private static final long serialVersionUID = 6924059181624616239L;
	int id;
	String title;
	String article_abstract;
	String fileName;
	User author;
	
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
	
	
	
}

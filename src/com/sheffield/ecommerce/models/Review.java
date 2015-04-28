package com.sheffield.ecommerce.models;
import java.io.Serializable;

public class Review implements Serializable {

	private static final long serialVersionUID = 6849934273148906689L;
	private int id;
	private String overallJudgement;
	private String reviewerExpertise;
	private String articleSummary;
	private String substantiveCriticism;
	private String smallErrors;
	private String commentsForEditor;
	private Article article;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOverallJudgement() {
		return overallJudgement;
	}
	public void setOverallJudgement(String overallJudgement) {
		this.overallJudgement = overallJudgement;
	}
	public String getReviewerExpertise() {
		return reviewerExpertise;
	}
	public void setReviewerExpertise(String reviewerExpertise) {
		this.reviewerExpertise = reviewerExpertise;
	}
	public String getArticleSummary() {
		return articleSummary;
	}
	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}
	public String getSubstantiveCriticism() {
		return substantiveCriticism;
	}
	public void setSubstantiveCriticism(String substantiveCriticism) {
		this.substantiveCriticism = substantiveCriticism;
	}
	public String getSmallErrors() {
		return smallErrors;
	}
	public void setSmallErrors(String smallErrors) {
		this.smallErrors = smallErrors;
	}
	public String getCommentsForEditor() {
		return commentsForEditor;
	}
	public void setCommentsForEditor(String commentsForEditor) {
		this.commentsForEditor = commentsForEditor;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
}

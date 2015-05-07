package com.sheffield.ecommerce.models;

import java.io.Serializable;
import com.sheffield.ecommerce.exceptions.InvalidModelException;

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
	private User reviewer;
	private String rejectReason;
	
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
	public User getReviewer() {
		return reviewer;
	}
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public void validateModel() throws InvalidModelException {
		if (article == null){
			throw new InvalidModelException("You must select an article to review.");
		}
		
		if (overallJudgement == null || overallJudgement.isEmpty()){
			throw new InvalidModelException("You must select an overall judgement.");
		}
		if (reviewerExpertise == null || reviewerExpertise.isEmpty()){
			throw new InvalidModelException("You must select expertise level.");
		}
		if (articleSummary == null || articleSummary.isEmpty()){
			throw new InvalidModelException("You must write an article summary.");
		}
		if (substantiveCriticism == null || substantiveCriticism.isEmpty()){
			throw new InvalidModelException("You must write some criticism.");
		}
		if (smallErrors == null || smallErrors.isEmpty()){
			throw new InvalidModelException("You must write any errors you found.");
		}
		
		
	}
	
}

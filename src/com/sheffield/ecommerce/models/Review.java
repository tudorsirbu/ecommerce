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
	 * @return the overallJudgement
	 */
	public String getOverallJudgement() {
		return overallJudgement;
	}

	/**
	 * @param overallJudgement the overallJudgement to set
	 */
	public void setOverallJudgement(String overallJudgement) {
		this.overallJudgement = overallJudgement;
	}

	/**
	 * @return the reviewerExpertise
	 */
	public String getReviewerExpertise() {
		return reviewerExpertise;
	}

	/**
	 * @param reviewerExpertise the reviewerExpertise to set
	 */
	public void setReviewerExpertise(String reviewerExpertise) {
		this.reviewerExpertise = reviewerExpertise;
	}

	/**
	 * @return the articleSummary
	 */
	public String getArticleSummary() {
		return articleSummary;
	}

	/**
	 * @param articleSummary the articleSummary to set
	 */
	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	/**
	 * @return the substantiveCriticism
	 */
	public String getSubstantiveCriticism() {
		return substantiveCriticism;
	}

	/**
	 * @param substantiveCriticism the substantiveCriticism to set
	 */
	public void setSubstantiveCriticism(String substantiveCriticism) {
		this.substantiveCriticism = substantiveCriticism;
	}

	/**
	 * @return the smallErrors
	 */
	public String getSmallErrors() {
		return smallErrors;
	}

	/**
	 * @param smallErrors the smallErrors to set
	 */
	public void setSmallErrors(String smallErrors) {
		this.smallErrors = smallErrors;
	}

	/**
	 * @return the commentsForEditor
	 */
	public String getCommentsForEditor() {
		return commentsForEditor;
	}

	/**
	 * @param commentsForEditor the commentsForEditor to set
	 */
	public void setCommentsForEditor(String commentsForEditor) {
		this.commentsForEditor = commentsForEditor;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the reviewer
	 */
	public User getReviewer() {
		return reviewer;
	}

	/**
	 * @param reviewer the reviewer to set
	 */
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * This method will check that the model conforms to validation rules. If it does not an execption is thrown with the details
	 * @throws InvalidModelException Contains the validation rule that was broken
	 */
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

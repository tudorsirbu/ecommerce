<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="cons" class="com.sheffield.ecommerce.helpers.Constant"	scope="session" />

<t:template title="Article Details">
	<div class="row">
		<div class="col-sm-12">
		
		
			<div class="page-header">
				<h1>
					${article.title}
					<c:choose>
						<c:when test="${editor == true}">
						</c:when>
						<c:otherwise>
              <c:if test="${article.numberOfRevisions > 0}">
                <small>(Revision ${article.numberOfRevisions})</small>
              </c:if>
							<c:choose>
								<c:when test="${downloadable == true}">
									<a href="${pageContext.request.contextPath}/uploads/${article.latestFileName}" target="_blank">
									  <span class="glyphicon glyphicon-download-alt">&nbsp;</span>
									</a>
									<br />
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/DownloadsManager?article_id=${article.id}">
										<span class="glyphicon glyphicon-download-alt">&nbsp;</span>
									</a>
									<br />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</h1>
			</div>
			
			
			<div class="well well-sm">
				<h5>
					<b> Written by ${author.firstName} ${author.lastName} </b> | <a href="mailto:${author.email}">${author.email}</a>
				</h5>
			</div>
			
			
			<div class="well well-lg">${article.article_abstract}</div>


			<!-- Only show reviews to the author or the editors -->
			<c:if test="${(currentUser.id == author.id or currentUser.role == cons.editor) and not empty article.reviews}">
				<h2>Reviews</h2>
				<div class="well well-lg">
					<c:forEach items="${article.reviews}" var="review">

						<div class="row">
							<div class="col-sm-3">
								<b>Overall Judgement</b>
							</div>
							<div class="col-sm-9">${review.overallJudgement}</div>
						</div>
						<div class="row">
							<div class="col-sm-3">
								<b>Reviewer Expertise</b>
							</div>
							<div class="col-sm-9">${review.reviewerExpertise}</div>
						</div>
						<div class="row">
							<div class="col-sm-3">
								<b>Article Summary</b>
							</div>
							<div class="col-sm-9">${review.articleSummary}</div>
						</div>
						<div class="row">
							<div class="col-sm-3">
								<b>Substantive Criticism</b>
							</div>
							<div class="col-sm-9">${review.substantiveCriticism}</div>
						</div>
						<div class="row">
							<div class="col-sm-3">
								<b>Small Errors</b>
							</div>
							<div class="col-sm-9">${review.smallErrors}</div>
						</div>
						<!-- Only show the comments for editors to the editors -->
						<c:if test="${currentUser.role == cons.editor}">
							<div class="row">
								<div class="col-sm-3">
									<b>Comments for Editors</b>
								</div>
								<div class="col-sm-9">${review.commentsForEditor}</div>
							</div>
						</c:if>

						<br>

					</c:forEach>

					<!-- Show the revision button only if there are more than 3 reviews -->
					<c:if test="${fn:length(article.reviews) >= 3 or currentUser.role == cons.editor}">
						<a
							href="${pageContext.request.contextPath}/RevisionForm?articleId=${article.id}"
							class="btn btn-primary">Revise Article</a>
					</c:if>
				</div>
			</c:if>


			<c:if test="${currentUser.role == cons.editor and not empty reviewers}">
				<h2>This article is being reviewed by:</h2>
				<div class="well well-sm">
					<table class="table">
						<thead>
							<tr>
								<th>Reviewer</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="r" items="${reviewers}">
								<tr>
									<td><b> ${r.firstName} ${r.lastName}</b></td>
									<td>
									  <a aria-label="Left Align" href="${pageContext.request.contextPath}/RejectReviewerChoice?reviewer_id=${r.id}&article_id=${article.id}">
									    <span class="glyphicon glyphicon-remove"></span> Remove
									  </a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>

		</div>
	</div>
</t:template>
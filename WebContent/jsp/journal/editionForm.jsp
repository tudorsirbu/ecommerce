<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<t:template title="Edition Editor">
    <div class="page-header">
      <h1>${empty edition ? "New Edition" : "Edit Edition"}</h1>
    </div>
    
	  <form name="editionForm" method="post" class="form-horizontal">
	  
	    <div class="form-group">
	     <label for="publicationDate" class="control-label col-sm-2">Publication Date</label>
	     <div class="col-sm-4">
	       <input type="date" name="publicationDate" id="publicationDate" class="form-control" required="required" value="<fmt:formatDate value='${edition.publicationDate}' type='date' pattern='dd/MM/yyyy'/>">
	     </div>
	    </div>
  		<div class="row">
			<div class="form-actions col-sm-2 col-sm-offset-5">
		      <button class="btn btn-primary" type="submit">${empty edition ? "Save Edition" : "Update Edition"}</button>
			</div>
		</div>
	  </form>
	  
	<c:if test="${not empty edition}">
		<br><br>
		
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4 style="margin: 0">Articles</h4>
			</div>
			<div class="panel-body">
				<table class="table">
					<thead>
						<tr>
							<th>Article</th>
							<th>Actions</th>
						<tr>
					</thead>
					<tbody>
						<c:forEach items="${editionArticles}" var="article">
							<tr>
								<td>${article.title}</td>
								<td>
									<a href="${pageContext.request.contextPath}/article/show?article_id=${article.id}">View Article</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:choose>
					<c:when test="${empty approvedArticles}">
						<p>There are no articles suitable for publishing at this time.</p>
					</c:when>
					<c:otherwise>
						<form name="assignArticleForm" method="post" class="form-inline">
							<div class="form-group">
								<label for="approvedArticle" class="control-label">Select an approved article to assign to this edition:</label>
								<select name="approvedArticle" id="approvedArticle" class="form-control">
									<c:forEach items="${approvedArticles}" var="article">
										<option value="${article.id}">${fn:substring(article.title, 0, 75)}</option>
									</c:forEach>
								</select>
							</div>
						    <button class="btn btn-primary" type="submit">Assign Article</button>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>		
	</c:if>
	
	
	


</t:template>
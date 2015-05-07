<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
						<c:forEach items="${articles}" var="article">
							<tr>
								<td>${article.title}</td>
								<td>
									<a href="${pageContext.request.contextPath}/article/show?article_id=${article.id}">View Article</a>
								</td>
							</tr>
						</c:forEach>
						<form name="assignArticleForm" method="post" class="form-horizontal">
						<tr>
							
	  							<td>
	  								<div class="form-group">
		  								<label for="approvedArticle" class="control-label col-sm-2">Approved Articles</label>	
		  								<select name="approvedArticle" id="approvedArticle" class="form-control">
										  	<option>1</option>
										  	<option>2</option>
										  	<option>3</option>
										  	<option>4</option>
											<option>5</option>
										</select>
									</div>
								</td>
								<td>
									<div class="form-actions">
								      <button class="btn btn-primary" type="submit">Assign Article</button>
									</div>
								</td>
						</tr>
						</form>
					</tbody>
				</table>
			</div>
		</div>		
	</c:if>
	
	
	


</t:template>
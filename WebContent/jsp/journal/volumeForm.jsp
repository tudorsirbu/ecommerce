<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template title="Volume Editor">
    <div class="page-header">
      <h1>${empty volume ? "New Volume" : "Edit Volume"}</h1>
    </div>
    
	  <form name="volumeForm" method="post" class="form-horizontal">
	  
	    <div class="form-group">
	     <label for="publicationDate" class="control-label col-sm-2">Publication Date</label>
	     <div class="col-sm-4">
	       <input type="date" name="publicationDate" id="publicationDate" class="form-control" required="required" value="<fmt:formatDate value='${volume.publicationDate}' type='date' pattern='yyyy-MM-dd'/>">
	     </div>
	    </div>
  		<div class="row">
			<div class="form-actions col-sm-2 col-sm-offset-5">
		      <button class="btn btn-primary" type="submit">${empty volume ? "Save Volume" : "Update Volume"}</button>
			</div>
		</div>
	  </form>
	  
	<c:if test="${not empty volume}">
		<br><br>
		
		<a class="btn btn-primary pull-right" href="${pageContext.request.contextPath}/EditionEditor?vol=${volume.volumeId}">Add Edition</a>
	
		<br><br>
	
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4 style="margin: 0">Editions</h4>
			</div>
			<div class="panel-body">
				<table class="table">
					<thead>
						<tr>
							<th>Edition Date</th>
							<th>Actions</th>
						<tr>
					</thead>
					<tbody>
						<c:forEach items="${volume.editions}" var="edition" varStatus="loopCount">
							<tr>
								<td><fmt:formatDate type="date" dateStyle="long" value="${edition.publicationDate}"/></td>
								<td>
									<a href="${pageContext.request.contextPath}/EditionEditor?id=${edition.editionId}&vol=${volume.volumeId}">Edit Edition</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>

</t:template>
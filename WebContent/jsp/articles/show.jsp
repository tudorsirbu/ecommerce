<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
			      	<c:choose>
					      <c:when test="${downloadable == true}">
						      <a href="${pageContext.request.contextPath}/uploads/${article.fileName}" target="_blank">
					          	<span class="glyphicon glyphicon-download-alt">&nbsp;</span>
					          </a>
						      <br />
					      </c:when>
					      <c:otherwise>
					      <a href="${pageContext.request.contextPath}/DownloadsManager?article_id=${article.id}">
			              		<span class="glyphicon glyphicon-download-alt">&nbsp;Download</span>
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
	      		<b> Written by ${author.firstName}  ${author.lastName} </b>
	      		|
	      		<a href="mailto:${author.email}">${author.email}</a>
	   		</h5>
	       </div>
      	   <div class="well well-lg">${article.article_abstract}</div>
      <c:choose>
	        <c:when test="${editor == true}">
	        	<h2> This article is being reviewed by:</h2>
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
		              		<td>
		              			<b> ${r.firstName} ${r.lastName}</b>
		              		</td>
		              		<td>
		              			<a aria-label="Left Align" href="${pageContext.request.contextPath}/RejectReviewerChoice?reviewer_id=${r.id}&article_id=${article.id}">
		              				<span class="glyphicon glyphicon-remove"></span> Remove
		              			</a>
		              		</td>
		            	</tr>
		          	</c:forEach>
			        </tbody>
			      </table>
	        </c:when>
	        <c:otherwise>
	        </c:otherwise>
	    </c:choose>
    </div>
  </div>
</t:template>
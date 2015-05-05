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
      
    </div>
  </div>
</t:template>
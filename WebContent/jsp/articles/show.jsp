<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Article Details">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>
        	${article.title}
	        <a href="${pageContext.request.contextPath}/uploads/${article.fileName}" target="_blank">
	        	<span class="glyphicon glyphicon-download-alt">&nbsp;</span>
	        </a>
        </h1>
      </div>
      <div class="well well-sm">
      	<h5>
      		<b> Written by ${author.firstName}  ${author.lastName} </b>
      		<a href="mailto:${author.email}">${author.email}</a>
   		</h5>
      </div>
      <div class="well well-lg">${article.article_abstract}</div>
      
    </div>
  </div>
</t:template>
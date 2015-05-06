<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Articles">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>${journal.title}</h1>
      </div>
      <c:forEach var="volume" items="${volumes}">
      	<c:if test="${not empty volume.editions}">
      		<c:forEach var="edition" items="${volume.editions}">
      			<div class="panel panel-default">
			    	<div class="panel-heading">Volume #${volume.volumeNumber} - Edition #${edition.editionNumber}</div>
					<div class="panel-body">
					    <table class="table">
					        <thead>
					          <tr>
					            <th>Title</th>
					            <th>Abstract</th>     
					          </tr>
					        </thead>
					        <tbody>
					          <c:forEach var="a" items="${articles}">
					            <tr>
					              <td>
					              	<a href="${pageContext.request.contextPath}/uploads/${a.latestFileName}">${a.title}</a>
					              </td>
					              <td style="white-space: pre-wrap;">${a.article_abstract}</td>
					            </tr>
					          </c:forEach>
					        </tbody>
					      </table>
					</div>
		  		</div>
      		</c:forEach>
      	</c:if>
	  </c:forEach>
      
    </div>
  </div>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Articles">
  <div class="row">
    <div class="col-sm-12">
      <c:choose>
			<c:when test="${editor != true}">
				<div class="page-header">
				  <h1>Articles you can review</h1>
				</div>
			</c:when>
			<c:otherwise>
				<div class="page-header">
				  <h1>Articles you can review or reject reviewers for</h1>
				</div>
			</c:otherwise>
	  </c:choose>
	  <table class="table">
					<thead>
						<tr>
							<th>Title</th>
							<th>Actions</th>    
						</tr>
					</thead>
					<tbody>
						<c:forEach var="a" items="${articles}">
							<tr>
							<td>
							<a href="${pageContext.request.contextPath}/article/show?article_id=${a.id}">${a.title}</a>
							</td>
							<td>
							<a href="${pageContext.request.contextPath}/DownloadsManager?article_id=${a.id}">
								<span class="glyphicon glyphicon-download-alt">&nbsp;Download</span>
							</a>
							</td>
							</tr>
						</c:forEach>
			        </tbody>
		        </table>
    </div>
  </div>
</t:template>
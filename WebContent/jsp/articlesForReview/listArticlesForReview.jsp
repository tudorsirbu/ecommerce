<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<t:template title="Articles">
  <div class="row">
    <div class="col-sm-12">
    
      <c:if test="${not empty articlesBeingReviewed}">
	      <h2>Articles you're currently reviewing</h2>

        <table class="table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Actions</th>    
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${articlesBeingReviewed}" var="a">
              <tr>
                <td>
                  <a href="${pageContext.request.contextPath}/article/show?article_id=${a.id}">
	                  ${a.title}
	                  <c:if test="${article.numberOfRevisions > 0}">
			                <small>(Revision ${article.numberOfRevisions})</small>
			              </c:if>
                  </a>
                </td>
                <td>
                  <a class="btn btn-link" href="${pageContext.request.contextPath}/DownloadsManager?article_id=${a.id}">
                    <span class="glyphicon glyphicon-download-alt">&nbsp;</span>Download
                  </a>
                  <a class="btn btn-link"  href="${pageContext.request.contextPath}/ReviewForm?article_id=${a.id}">
                    <span class="glyphicon glyphicon-check">&nbsp;</span>Review
                  </a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>
    
      <br>
    
      <c:choose>
			<c:when test="${editor != true}">
			  <h2>Articles you can review</h2>
			</c:when>
			<c:otherwise>
			  <h2>Articles you can review or reject reviewers for</h2>
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
								<span class="glyphicon glyphicon-download-alt">&nbsp;</span> Download
							</a>
							</td>
							</tr>
						</c:forEach>
			        </tbody>
		        </table>
    </div>
  </div>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<t:template title="Articles">
  <div class="row">
    <div class="col-sm-12">
    
      <c:if test="${not empty articlesBeingReviewed}">
	      <h2>Articles you're currently reviewing</h2>
	      <p>These are the articles that you have committed to reviewing. You can download the latest copy as many times as you like but you must write a review for it by clicking the Review button and filling out the form.</p>
	      <p>If the author uploads a revision to their article, you must write a second review based on the changes made. You can view revision comments by clicking the article title.</p>

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
                  <c:choose>
                    <c:when test="${a.numberOfRevisions == 0}"> 
	                  <a class="btn btn-link"  href="${pageContext.request.contextPath}/ReviewForm?article_id=${a.id}">
	                    <span class="glyphicon glyphicon-check">&nbsp;</span>Review
	                  </a>
	                  </c:when>
	                  <c:otherwise>
	                    <a class="btn btn-link"  href="${pageContext.request.contextPath}/article/show?article_id=${a.id}">
                        <span class="glyphicon glyphicon-info-sign">&nbsp;</span>View feedback
                      </a>
	                  </c:otherwise>
                  </c:choose>
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
	  <p>These are the articles that you can choose to review. When uploading an article, you must review 3 other articles by different authors. You can view more details of an article such as its abstract by clicking its title. By clicking download, you are committing to review this article.</p>
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
							<a class="btn btn-link" href="${pageContext.request.contextPath}/article/show?article_id=${a.id}">${a.title}</a>
							</td>
							<td>
							<a class="btn btn-link" href="${pageContext.request.contextPath}/DownloadsManager?article_id=${a.id}">
								<span class="glyphicon glyphicon-download-alt"></span>Download
							</a>
							
							<c:if test="${editor == true}">
								<a class="btn btn-link" href="${pageContext.request.contextPath}/ReviewForm?article_id=${a.id}">
									<span class="glyphicon glyphicon-check"></span>Review
								</a>
							</c:if>
							</td>
							</tr>
						</c:forEach>
			        </tbody>
		        </table>
    </div>
  </div>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Review form">
  <c:if test="${empty sessionScope.errorMsg}">
	  <div class="row">
	    <div class="col-sm-4 col-sm-push-4">
	      <c:choose>
	        <c:when test="${article.numberOfRevisions == 1}">
	          <h2>Accept/Reject Article Revision</h2>
	        </c:when>
	        <c:otherwise>
	          <h2>Article Review Form</h2>
	        </c:otherwise>	      
	      </c:choose>

	      <form name="articleReview" action="ReviewForm" method="post" class="form-signin">
	        <input type="hidden" name="article_id" value="${param.article_id}" />

	        <c:choose>
	          <c:when test="${article.numberOfRevisions == 1}">
				      
				      <c:choose>
                <c:when test="${param.rejecting != null}">
				          <label for="overallJudgement">Overall judgement:</label>
		              <select name="overallJudgement" id="overallJudgement">
		                <option disabled selected>Please select</option>
		                <option value="indifferent">Indifferent</option>
		                <option value="detractor">Detractor</option>
		              </select><br>
              
                  <label for="articleErrors">Reject reason:</label><br>
                  <textarea name="rejectReason" rows="3" cols="48" placeholder="Please write the reasons why you are rejecting this article revision"></textarea>
                </c:when>
                <c:otherwise>
                  <label for="overallJudgement">Overall judgement:</label>
		              <select name="overallJudgement" id="overallJudgement">
		                <option disabled selected>Please select</option>
		                <option value="champion">Champion</option>
		                <option value="favourable">Favourable</option>
		              </select><br>
                  <input type="hidden" name="rejectReason" />
                </c:otherwise>
              </c:choose>
				      				    
				      
              <input type="hidden" name="reviewerExpertise" id="expertise" value="${lastReview.reviewerExpertise}" />
              <input type="hidden" name="articleSummary" value="${lastReview.articleSummary}" />
              <input type="hidden" name="articleCriticism" value="${lastReview.substantiveCriticism}" />
              <input type="hidden" name="articleErrors" value="${lastReview.smallErrors}" />
              <input type="hidden" name="secretComments" value="${lastReview.commentsForEditor}" />
          
	          </c:when>
	          <c:otherwise>
	          
	            <label for="overallJudgement">Overall judgement:</label>
              <select name="overallJudgement" id="overallJudgement">
                <option disabled selected>Please select</option>
                <option value="champion">Champion</option>
                <option value="favourable">Favourable</option>
                <option disabled>----------</option>
                <option value="indifferent">Indifferent</option>
                <option value="detractor">Detractor</option>
              </select><br>
              
				      <label for="reviewerExpertise">Expertise level:</label>
				        <select name="reviewerExpertise" id="expertise">
				          <option disabled selected>Please select</option>
				            <option value="expert">Expert</option>
				        <option value="knowledgeable">Knowledgeable</option>
				        <option value="outsider">Outsider</option>
				      </select><br>
				        
				      <label for="articleSummary">Article summary:</label><br>
				      <textarea name="articleSummary" rows="3" cols="48" placeholder="Please write a summary for the selected article and make sure to include any novel contributions of the article..."></textarea>
				          
				          <label for="articleCriticism">Substantive criticism:</label><br>
				      <textarea name="articleCriticism" rows="3" cols="48" placeholder="Please write any substantial criticism for the selected article..."></textarea>
				          
				          <label for="articleErrors">Small errors:</label><br>
				      <textarea name="articleErrors" rows="3" cols="48" placeholder="Please write add any errors that were found (typographical,or grammatical mistakes, etc.)"></textarea>
				          
				          <label for="secretComments">Comments for editor:</label><br>
				      <textarea name="secretComments" rows="3" cols="48" placeholder="Please write any comments that you wish only the editor to see..."></textarea>
 
              <input type="hidden" name="rejectReason" />
	          </c:otherwise>        
	        </c:choose>
 	
	        <br>
	        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit review</button>
	      </form>
	    </div>
	  </div>
  </c:if>
</t:template>
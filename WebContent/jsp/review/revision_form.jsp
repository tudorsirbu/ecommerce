<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Article Revision Form">
  <div class="row">
    <div class="col-sm-4 col-sm-push-4">
      <h2>Article Revision Form</h2>
      <form name="articleRevision" action="RevisionForm" method="post" class="form-revision">

      	<label for="revisionDetails">Revision details:</label><br>
        <textarea name="revisionDetails" rows="3" cols="48" placeholder="Please write in detail the corrections you have made based on the feedback from the reviewers..."></textarea>
        
      	<label for="fileUploadArticle" class="sr-only">Upload article</label>
        <input type="file" name="fileUploadArticle" id="fileUploadArticle" class="form-control" size="50" />
      	
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit revision</button>
      </form>
    </div>
  </div>
</t:template>
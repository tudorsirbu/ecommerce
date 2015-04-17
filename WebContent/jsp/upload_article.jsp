<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Upload article">
	<div class="row">
	    <div class="col-sm-4 col-sm-push-4">
	      <form name="upload_article" action="UploadArticle" method="post" class="form-upload-article" enctype="multipart/form-data">
				<h4>Article details</h4>
				<label for="inputArticleTitle" class="sr-only">Article title</label>
				<input type="text" name=""inputArticleTitle"" id="inputArticleTitle" class="form-control" placeholder="Article title" required="true" autofocus="">
				
				<label for="inputArticleAbstract" class="sr-only">Article abstract</label>
				<textarea name="inputArticleAbstract" id="inputArticleAbstract" class="form-control" placeholder="Please enter an article abstract" required="true" rows="10"></textarea>
				 
				<label for="fileUploadArticle" class="sr-only">Upload article</label>
				<input type="file" name="fileUploadArticle" id="fileUploadArticle" class="form-control" size="50" />
	
	        	<br>
		        <button class="btn btn-lg btn-primary btn-block" type="submit">Upload article</button>
			</form>
	    </div>
    </div>
</t:template>

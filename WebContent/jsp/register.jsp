<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Register">
	<form name="register" action="Register" method="post" class="form-signin" enctype="multipart/form-data">
   		<div class="col-sm-6 ">
     		<h4>Your details</h4>
      
	      	<label for="inputFirstName" class="sr-only">First name</label>
	        <input type="text" name="inputFirstName" id="inputFirstName" class="form-control" placeholder="First name" required="true" autofocus="">
	        <label for="inputLastName" class="sr-only">Last name</label>
	        <input type="text" name="inputLastName" id="inputLastName" class="form-control" placeholder="Last name" required="true" autofocus="">
      
	        <label for="inputEmail" class="sr-only">Email address</label>
	        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required="true" autofocus="">
	        <label for="inputPassword" class="sr-only">Password</label>
	        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required="true">
	        
	        <label for="inputPasswordConfirmation" class="sr-only">Password confirmations</label>
	        <input type="password" name="inputPasswordConfirmation" id="inputPasswordConfirmation" class="form-control" placeholder="Password confirmation" required="true">
		</div>
		<div class="col-sm-6">
        	<h4>Article details</h4>
        	<label for="inputArticleTitle" class="sr-only">Article title</label>
	        <input type="text" name=""inputArticleTitle"" id="inputArticleTitle" class="form-control" placeholder="Article title" required="true" autofocus="">
	        
	        <label for="inputArticleAbstract" class="sr-only">Article abstract</label>
	        <textarea name="inputArticleAbstract" id="inputArticleAbstract" class="form-control" placeholder="Please enter an article abstract" required="true" rows="10"></textarea>
	        
	        <label for="fileUploadArticle" class="sr-only">Upload article</label>
	        <input type="file" name="fileUploadArticle" id="fileUploadArticle" class="form-control" size="50" />
        </div>
        <div class="col-sm-2 pull-right">
        	<br>
	        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        </div>
	</form>
</t:template>
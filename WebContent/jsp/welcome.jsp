<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Home">
  <div class="row">
    <div class="col-sm-12">
      <c:if test="${not empty currentUser}">
        <div class="page-header">
        	<h1>Welcome back, ${currentUser.firstName}</h1>
      	</div>
      	<h3>Your email is "${currentUser.email}" and your id in the database is "${currentUser.id}"!</h3>
      </c:if> 
      <h2>Below are all the published articles for you to read:</h2>
    </div>
  </div>
</t:template>
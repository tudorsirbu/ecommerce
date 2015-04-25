<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="cons" class="com.sheffield.ecommerce.helpers.Constant" scope="session"/>

<t:template title="User Details">
  <c:if test="${not empty user}">
	  <div class="row">
	    <div class="col-sm-12">
	      <div class="page-header">       
	        <!-- Edit button should only be shown to editors or to the current user if this is their show page -->
	        <c:if test="${(currentUser.role == cons.editor) || (currentUser.id == user.id)}">
	          <a class="btn btn-primary pull-right" href="${pageContext.request.contextPath}/users/edit?id=${user.id}">Edit User</a>
	        </c:if>
	        <h1>User Details</h1>
	      </div>
	      
	      <table class="table">
	        <tbody>
	          <tr>
	            <td>First Name</td>
	            <td>${user.firstName}</td>
	          </tr>
	          <tr>
	            <td>Last Name</td>
	            <td>${user.lastName}</td>
	          </tr>
	          <tr>
	            <td>Email</td>
	            <td>${user.email}</td>
	          </tr>
	          <tr>
	            <td>Role</td>
	            <td>${user.roleName}</td>
	          </tr>
	        </tbody>
	      </table>
	    </div>
	  </div>
  </c:if>
</t:template>
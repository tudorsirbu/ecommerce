<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="cons" class="com.sheffield.ecommerce.helpers.Constant" scope="session"/>

<t:template title="Edit User">
  <c:if test="${not empty user}">
    <div class="page-header">
      <h1>Edit User</h1>
    </div>
    
	  <form name="register" method="post" class="form-horizontal">
	  
	    <div class="form-group">
	     <label for="firstName" class="control-label col-sm-2">First name</label>
	     <div class="col-sm-10">
	       <input type="text" name="firstName" id="firstName" class="form-control" placeholder="First name" required="" autofocus="" value="${user.firstName}">
	     </div>
	    </div>
	    
	    <div class="form-group">
	      <label for="lastName" class="control-label col-sm-2">Last name</label>
	      <div class="col-sm-10">
	        <input type="text" name="lastName" id="lastName" class="form-control" placeholder="Last name" required="" value="${user.lastName}">
	      </div>
      </div>
	     
		  <div class="form-group">
		    <label for="email" class="control-label col-sm-2">Email address</label>
		    <div class="col-sm-10">
		      <input type="email" name="email" id="email" class="form-control" placeholder="Email address" required="" value="${user.email}">
		    </div>
		  </div>
		  
		  <div class="form-group">
        <label for="password" class="control-label col-sm-2">Password</label>
        <div class="col-sm-10">
          <input type="password" name="password" id="password" class="form-control" placeholder="Password">
        </div>
      </div> 
		  
		  <div class="form-group">
        <label for="passwordConfirmation" class="control-label col-sm-2">Password confirmation</label>
        <div class="col-sm-10">
          <input type="password" name="passwordConfirmation" id="passwordConfirmation" class="form-control" placeholder="Password confirmation">
        </div>
      </div> 
		  
		  <!-- Only allow editors to modify a users role -->
		  <c:if test="${currentUser.role == cons.editor}">
			  <div class="form-group">
			    <label for="role" class="control-label col-sm-2">Role</label>
			    <div class="col-sm-10">
			      <select name="role" class="form-control">
				      <option value="0" ${user.role == 0 ? 'selected' : ''}>Author/Reviewer</option>
				      <option value="1" ${user.role == 1 ? 'selected' : ''}>Editor</option>
				    </select>
			    </div>
			  </div>
		  </c:if>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button class="btn btn-primary" type="submit">Update User</button>
		    </div>
		  </div>

	  </form>
  </c:if>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Register">
  <div class="row">
    <div class="col-sm-4 col-sm-push-4">
      <h2>Register</h2>
      <form name="register" action="Register" method="post" class="form-signin">
      	<label for="inputFirstName" class="sr-only">First name</label>
        <input type="text" name="inputFirstName" id="inputFirstName" class="form-control" placeholder="First name" required="required" autofocus="autofocus">
        <label for="inputLastName" class="sr-only">Last name</label>
        <input type="text" name="inputLastName" id="inputLastName" class="form-control" placeholder="Last name" required="required">
      
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required="required">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required="required">
        
        <label for="inputPasswordConfirmation" class="sr-only">Password confirmations</label>
        <input type="password" name="inputPasswordConfirmation" id="inputPasswordConfirmation" class="form-control" placeholder="Password confirmation" required="required">
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      </form>
    </div>
  </div>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Register">
  <div class="row">
    <div class="col-sm-4 col-sm-push-4">
      <h2>Register</h2>
      <form name="register" action="Register" method="post" class="form-signin">
        <label for="inputFirstName" class="sr-only">First name</label>
        <input type="text" name="inputFirstName" id="inputFirstName" class="form-control" placeholder="First name" required="" autofocus="">
        <label for="inputLastName" class="sr-only">Last name</label>
        <input type="text" name="inputLastName" id="inputLastName" class="form-control" placeholder="Last name" required="" autofocus="">
      
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required="">
        
        <label for="inputPasswordConfirmation" class="sr-only">Password confirmations</label>
        <input type="password" name="inputPasswordConfirmation" id="inputPasswordConfirmation" class="form-control" placeholder="Password confirmation" required="">
        <br>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      </form>
    </div>
  </div>
</t:template>
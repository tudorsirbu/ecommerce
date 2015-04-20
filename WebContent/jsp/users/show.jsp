<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="User Details">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
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
</t:template>
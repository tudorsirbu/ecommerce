<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:template title="Users">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>Users</h1>
      </div>
      
      <table class="table">
        <thead>
          <tr>
            <th>First Name</th>
            <th>Surname</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>    
          </tr>
        </thead>
        <tbody>
          <c:forEach var="user" items="${users}">
            <tr>
              <td>${user.firstName}</td>
              <td>${user.lastName}</td>
              <td>${user.email}</td>
              <td>${user.roleName}</td>
              <td>
                <a href="${pageContext.request.contextPath}/users?id=${user.id}">View</a>
                |
                <a href="${pageContext.request.contextPath}/users/edit?id=${user.id}">Edit</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</t:template>
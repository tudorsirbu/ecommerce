<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="Home">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>Welcome back, ${currentUser.firstName}</h1>
      </div>
      
      <h3>Your email is "${currentUser.email}" and your id in the database is "${currentUser.id}"!</h3> 
    </div>
  </div>
</t:template>
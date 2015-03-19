<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<%@ attribute name="title" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link rel = "stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel = "stylesheet" href="${pageContext.request.contextPath}/css/application.css">
  </head>

  <body>

    <!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">Link 1</a></li>
            <li><a href="#">Link 2</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <!-- Begin page content -->
    <div class="container">
      <c:if test="${not empty errorMsg}">
    	<div class="alert alert-danger" role="alert">Error: ${errorMsg}</div>
  	  </c:if>
  	  <c:if test="${not empty successMsg}">
    	<div class="alert alert-success" role="alert">${successMsg}</div>
  	  </c:if>
      <jsp:doBody/>
    </div>

    <footer class="footer">
      <div class="container">
        <p class="text-muted"></p>
      </div>
    </footer>
  </body>
</html>
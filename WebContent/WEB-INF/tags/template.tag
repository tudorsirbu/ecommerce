<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="title"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${title}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/application.css">
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
			<a class="navbar-brand" href="Home">Electronic Journal</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<c:if test="${sessionScope.currentUser != null && sessionScope.currentUser.role == 1}">
					<li class="${fn:endsWith(pageContext.request.requestURI, 'manage.jsp') ? 'active' : ''}"><a href="ManageJournal">Manage Journal</a></li>
				</c:if>
				<li><a href="#">Link 1</a></li>
				<li><a href="#">Link 2</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${sessionScope.currentUser != null}">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Welcome back, ${sessionScope.currentUser.firstName}<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li>
									<a href="${pageContext.request.contextPath}/users?id=${sessionScope.currentUser.id}">My	Account</a>
								</li>
								<c:if test="${sessionScope.currentUser.role == 1}">
									<li><a href="${pageContext.request.contextPath}/users">Users</a></li>
								</c:if>
								<li class="divider"></li>
								<li><a href="Logout" title="Log out of the system">Logout</a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<li><a href="Login">Login</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!--/.nav-collapse -->
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
		<jsp:doBody />
	</div>

	<footer class="footer">
		<div class="container">
			<p class="text-muted"></p>
		</div>
	</footer>

	<!-- Import scripts at the bottom of the page to increase page load times -->
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
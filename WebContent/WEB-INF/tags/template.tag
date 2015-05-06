<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="title"%>

<!DOCTYPE HTML>

<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css">
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
			<a class="navbar-brand" href="${pageContext.request.contextPath}/Home">Electronic Journal</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<c:if test="${sessionScope.currentUser != null}">
					<c:if test="${sessionScope.currentUser.role == 1}">
						<li class="${fn:endsWith(pageContext.request.requestURI, 'manage.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/ManageJournal">Manage Journal</a></li>
						<li class="${fn:endsWith(pageContext.request.requestURI, 'listArticlesForReview.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/articlesForReview">Review Articles</a></li>
						<li class="${fn:endsWith(pageContext.request.requestURI, '') ? 'active' : ''}"><a href="${pageContext.request.contextPath}">Approve Articles</a></li>
					</c:if>
					<c:if test="${sessionScope.currentUser.role == 0}">
						<li class="${fn:endsWith(pageContext.request.requestURI, 'journal/about.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/AboutJournal">About Journal</a></li>
						<li class="${fn:endsWith(pageContext.request.requestURI, 'upload_article.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/UploadArticle">Upload Article</a></li>
						<li class="${fn:endsWith(pageContext.request.requestURI, 'listArticlesForReview.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/articlesForReview">Review Articles</a></li>
						<li class="${fn:endsWith(pageContext.request.requestURI, 'articles/list.jsp') ? 'active' : ''}"><a href="${pageContext.request.contextPath}/articles">My Articles</a></li>
					</c:if>
				</c:if>
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
								<li><a href="${pageContext.request.contextPath}/Logout" title="Log out of the system">Logout</a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/Login">Upload Article/Login</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<!-- Begin page content -->
	<div class="container">
		<c:if test="${not empty sessionScope.errorMsg}">
			<div class="alert alert-danger" role="alert">Error: ${sessionScope.errorMsg}</div>
		</c:if>
		<c:if test="${not empty sessionScope.successMsg}">
			<div class="alert alert-success" role="alert">${sessionScope.successMsg}</div>
		</c:if>
		<c:remove var="errorMsg" scope="session" />
		<c:remove var="successMsg" scope="session" />
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
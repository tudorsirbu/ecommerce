<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template title="About Journal">
	<div class="row">
		<div class="col-sm-12">
			<div class="page-header">
				<h1>About The Journal</h1>
			</div>
			<h2>${journal.title}</h2>

			<h3>Academic Aims</h3>
			<p>${journal.academicAims}</p>
			<br>
			<h3>Submission Guidelines</h3>
			<p>${journal.submissionGuidelines}</p>
			
		</div>
	</div>
</t:template>
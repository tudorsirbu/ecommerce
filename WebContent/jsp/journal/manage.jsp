<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template title="Journal Management">
	<div class="row">
		<div class="col-sm-12">
			<div class="page-header">
				<h1>Journal Management</h1>
			</div>

			<form name="editJournal" action="ManageJournal" method="post"
				class="form-signin">
				<label for="journalTitle">Title:</label> 
				<input type="text" name="journalTitle" id="journalTitle" class="form-control" placeholder="Enter a title for the journal" required="required" autofocus="autofocus" value="${journal.title}">
				<br> 
				<label for="journalAims">Aims and Scope:</label>
				<textarea name="journalAims" id="journalAims" class="form-control"	style="max-width:100%;" placeholder="Describe the aims and scope of the journal" required="required">${journal.academicAims}</textarea>
				<br>
				<label for="submissionGuidelines">Submission Guidelines</label>
				<textarea name="submissionGuidelines" id="submissionGuidelines" class="form-control"	style="max-width:100%;" placeholder="Provide some submission guidelines for users" required="required">${journal.submissionGuidelines}</textarea>

				<br>
				<div class="row">
					<div class="form-actions col-sm-2 col-sm-offset-5">
						<button class="btn btn-primary btn-block" type="submit">Update Journal</button>
					</div>
				</div>
			</form>

			<br><br>

			<a href="${pageContext.request.contextPath}/VolumeEditor" class="btn btn-primary pull-right">Add Volume</a>
			
			<br><br>
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 style="margin: 0">Volumes and Editions</h4>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>Volume</th>
								<th>Edition</th>
								<th>Publication Date</th>
								<th>Actions</th>
							<tr>
						</thead>
						<tbody>
							<c:forEach items="${volumes}" var="volume">
								<c:choose>
									<c:when test="${empty volume.editions}">
										<tr>
											<td>${volume.volumeNumber} - <fmt:formatDate type="date" pattern="yyyy" value="${volume.publicationDate}"/></td>
											<td> No editions published</td>
											<td>N/A</td>
											<td><a href="${pageContext.request.contextPath}/VolumeEditor?id=${volume.volumeId}">Edit Volume</a></td>
										<tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${volume.editions}" var="edition" varStatus="loopCount">
											<tr>
												<td>
													<c:if test="${loopCount.count eq 1}">${volume.volumeNumber} - <fmt:formatDate type="date" pattern="yyyy" value="${volume.publicationDate}"/></c:if>
												</td>
												<td>Edition: ${edition.editionNumber}</td>
												<td><fmt:formatDate type="date" dateStyle="long" value="${edition.publicationDate}"/></td>
												<td>
													<a href="${pageContext.request.contextPath}/VolumeEditor?id=${volume.volumeId}">Edit Volume</a>
													|
													<a href="${pageContext.request.contextPath}/EditionEditor?id=${edition.editionId}&vol=${volume.volumeId}">Edit Edition</a>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>
</t:template>
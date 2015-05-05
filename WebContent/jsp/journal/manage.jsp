<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:template title="Journal Management">
	<div class="row">
		<div class="col-sm-12">
			<div class="page-header">
				<h1>Journal Management</h1>
			</div>

			<form name="editJournal" action="ManageJournal" method="post"
				class="form-signin">
				<label for="journalTitle">Title:</label> <input type="text"
					name="journalTitle" id="journalTitle" class="form-control"
					placeholder="Journal Title" required="required"
					autofocus="autofocus" value="${journal.title}"> <br> <label
					for="journalAims">Aims and Scope:</label>
				<textarea name="journalAims" id="journalAims" class="form-control"	style="max-width:100%;" placeholder="Journal Aims and Scope" required="required">${journal.academicAims}</textarea>

				<br>
				<div class="row">
					<div class="form-actions col-sm-2 col-sm-offset-5">
						<button class="btn btn-primary btn-block" type="submit">Update Journal</button>
					</div>
				</div>
			</form>

			<br><br>

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 style="margin: 0">Volumes and Editions</h4>
				</div>
				<div class="panel-body">
					<table class="table">
						<thead>
							<tr>
								<th>Volume Number</th>
								<th>Edition Number</th>
								<th>Action</th>
							<tr>
						</thead>
						<tbody>
							<c:forEach items="${volumes}" var="volume">
								<c:forEach items="${volume.editions}" var="edition">
									<tr>
										<td>${volume.volumeId}</td>
										<td>${edition.name}</td>
										<td>
											<a href="${pageContext.request.contextPath}/VolumeEditor">Edit Volume</a>
											|
											<a href="${pageContext.request.contextPath}/EditionEditor">Edit Edition</a>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>
</t:template>
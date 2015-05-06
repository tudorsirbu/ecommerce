<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template title="About Journal">
	<div class="row">
		<div class="col-sm-12">
			<div class="page-header">
				<h1>Accessibility</h1>
			</div>
			
			<h3>Access Keys</h3>
			<p>This website is designed with access keys in order to make it accessible to more people. Access keys allow you to navigate around the website using your keyboard instead of your mouse. To use access keys, hold down the key combination relevant to your browser (see below) and then press the access key for the page you want to visit. For example, to go to the home page using Internet Explorer, press the ALT key and 1. Please note, for some browsers you must press enter after performing the key combination.</p>
			<br>
			
			<div class="row">
				<div class="col-sm-8">
					<div class="panel panel-default">
						<table class="table">
					  	<thead>
					  		<tr>
					  			<th>Browser</th>
					  			<th>Key Combination</th>
					  		</tr>
					  	</thead>
					  	<tbody>
					  		<tr>
					  			<td>Internet Explorer</td>
					  			<td><kbd>ALT</kbd> + [accesskey]</td>
					  		</tr>
					  		<tr>
					  			<td>Google Chrome</td>
					  			<td>
					  				<kbd>ALT</kbd> + [accesskey] (Windows &amp; Linux)
					  				<br><kbd>CTRL</kbd> + <kbd>ALT</kbd> + [accesskey] (Mac)
				  				</td>
					  		</tr>
					  		<tr>
					  			<td>Firefox</td>
					  			<td>
					  				<kbd>ALT</kbd> + <kbd>SHIFT</kbd> + [accesskey] (Windows &amp; Linux)
					  				<br><kbd>CTRL</kbd> + <kbd>ALT</kbd> + [accesskey] (Mac)</td>
					  		</tr>
					  		<tr>
					  			<td>Safari</td>
					  			<td>
					  				<kbd>ALT</kbd> + [accesskey] (Windows)
					  				<br><kbd>CTRL</kbd> + <kbd>ALT</kbd> + [accesskey] (Mac)</td>
					  		</tr>
					  		<tr>
					  			<td>Opera</td>
					  			<td>
					  				<kbd>ALT</kbd> + [accesskey] (Opera 15+)
					  				<br><kbd>SHIFT</kbd> + <kbd>ESC</kbd> + [accesskey] (Opera 12.1 and below)
					  			</td>
					  		</tr>
					  	</tbody>
						</table>	
					</div>
				</div>
		
				<div class="col-sm-4">
					<div class="panel panel-default">
						<table class="table">
					  	<thead>
					  		<tr>
					  			<th>Access Keys</th>
					  		</tr>
					  	</thead>
					  	<tbody>
					  		<tr><td>0 - Accessibility</td></tr>
					  		<tr><td>1 - Home</td></tr>
					  		<c:if test="${sessionScope.currentUser != null}">
								<c:if test="${sessionScope.currentUser.role == 1}">
									<tr><td>2 - Manage Journal</td></tr>
									<tr><td>3 - Review Articles</td></tr>
									<tr><td>4 - Approve Articles</td></tr>
								</c:if>
								<c:if test="${sessionScope.currentUser.role == 0}">
									<tr><td>2 - About Journal</td></tr>
									<tr><td>3 - Upload Article</td></tr>
									<tr><td>4 - Review Articles</td></tr>
									<tr><td>5 - My Articles</td></tr>
								</c:if>
							</c:if>
					  	</tbody>
						</table>	
					</div>
				</div>
			</div>
		
			<h3>Text Size</h3>
			<p>Most modern browsers allow you to increase the text size on a webpage. To do this, hold down CTRL and press the <kbd>+</kbd> [plus] key. To decrease the text size, hold down CTRL and press the <kbd>-</kbd> [minus] key. In some browsers, you may need to go to the View menu at the top, followed by the zoom menu. There, you will find options to increase and decrease the text size.</p>

		</div>
	</div>
</t:template>
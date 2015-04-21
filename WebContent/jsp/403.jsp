<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="403: Access Denied">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>403</h1>
      </div>
      
      <h3>Access Denied: You are not permitted to view this page.</h3>
    </div>
  </div>
</t:template>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template title="404: Not Found">
  <div class="row">
    <div class="col-sm-12">
      <div class="page-header">
        <h1>404</h1>
      </div>
      
      <h3>Not Found: The page you requested could not be found.</h3>
    </div>
  </div>
</t:template>
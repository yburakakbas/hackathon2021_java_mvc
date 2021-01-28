<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-4.0.0-dist/css/bootstrap.min.css">

</head>
<body class="col-md-12">

<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>

<div class="page-title">Category</div>

<c:if test="${not empty errorMessage }">
    <div class="error-message">
            ${errorMessage}
    </div>
</c:if>

<form:form modelAttribute="subcategory" method="POST" enctype="multipart/form-data">
    <table style="text-align:left;">
        <tr>
            <td>Description *</td>
            <td><form:input path="description"/></td>
            <td><form:errors path="description" class="error-message"/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit"/> <input type="reset"
                                                             value="Reset"/></td>
        </tr>
    </table>
</form:form>


<jsp:include page="_footer.jsp"/>

</body>
</html>
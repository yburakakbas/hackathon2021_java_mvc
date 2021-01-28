<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>


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

<div class="page-title">Product</div>

<c:if test="${not empty errorMessage }">
    <div class="error-message">
            ${errorMessage}
    </div>
</c:if>

<form:form modelAttribute="productDetails" method="POST" enctype="multipart/form-data">
    <table style="text-align:left;">
        <security:authorize access="hasRole('ROLE_MANAGER')">
            <tr>
                <td>Product *</td>
                <td><form:input path="product.name"/></td>
                <td><form:errors path="product.name" class="error-message"/></td>
            </tr>

            <tr>
                <td>Description *</td>
                <td><form:input path="description"/></td>
                <td><form:errors path="description" class="error-message"/></td>
            </tr>

            <tr>
                <td>Brand *</td>
                <td><form:input path="brand.name"/></td>
                <td><form:errors path="brand.name" class="error-message"/></td>
            </tr>

            <tr>
                <td>Category *</td>
                <td><form:input path="category.description"/></td>
                <td><form:errors path="category.description" class="error-message"/></td>
            </tr>

            <tr>
                <td>&nbsp;</td>
                <td><input type="submit" value="Submit"/> <input type="reset"
                                                                 value="Reset"/></td>
            </tr>
        </security:authorize>
        <security:authorize access="!hasRole('ROLE_MANAGER')">
            <tr>
                <td>Product *</td>
                <td><form:input disabled="true" readonly="true" path="product.name"/></td>
                <td><form:errors path="product.name" class="error-message"/></td>
            </tr>

            <tr>
                <td>Description *</td>
                <td><form:input disabled="true" readonly="true" path="description"/></td>
                <td><form:errors path="description" class="error-message"/></td>
            </tr>

            <tr>
                <td>Brand *</td>
                <td><form:input disabled="true" readonly="true" path="brand.name"/></td>
                <td><form:errors path="brand.name" class="error-message"/></td>
            </tr>

            <tr>
                <td>Category *</td>
                <td><form:input disabled="true" readonly="true" path="category.description"/></td>
                <td><form:errors path="category.description" class="error-message"/></td>
            </tr>
        </security:authorize>
    </table>
</form:form>


<jsp:include page="_footer.jsp"/>

</body>
</html>
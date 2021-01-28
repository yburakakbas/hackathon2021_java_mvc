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

<div class="page-title">Product</div>

<c:if test="${not empty errorMessage }">
    <div class="error-message">
            ${errorMessage}
    </div>
</c:if>

<form:form modelAttribute="productForm" method="POST" enctype="multipart/form-data">
    <table style="text-align:left;">
        <tr>
            <td>Code *</td>
            <td style="color:red;">
                <c:if test="${not empty productForm.code}">
                    <form:hidden path="code"/>
                    ${productForm.code}
                </c:if>
                <c:if test="${empty productForm.code}">
                    <form:input path="code"/>
                    <form:hidden path="newProduct"/>
                </c:if>
            </td>
            <td><form:errors path="code" class="error-message"/></td>
        </tr>

        <tr>
            <td>Name *</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" class="error-message"/></td>
        </tr>

        <tr>
            <td>Price *</td>
            <td><form:input path="price"/></td>
            <td><form:errors path="price" class="error-message"/></td>
        </tr>

        <tr>
            <td>Category *</td>
            <td>
                <form:select path="category.id">
                    <c:forEach var="category" items="${categoryList}">
                        <form:option value="${category.id}">${category.description}</form:option>
                    </c:forEach>
                </form:select>
            </td>
            <td><form:errors path="category.id" class="error-message"/></td>
        </tr>

        <tr>
            <td>Subcategory *</td>
            <td>
                <form:select path="subcategory.id">
                    <c:forEach var="subcategory" items="${subcategoryList}">
                        <form:option value="${subcategory.id}">${subcategory.description}</form:option>
                    </c:forEach>
                </form:select>
            </td>
            <td><form:errors path="subcategory.id" class="error-message"/></td>
        </tr>

        <tr>
            <td>Image</td>
            <td>
                <img src="${pageContext.request.contextPath}/productImage?code=${productForm.code}" width="100"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Upload Image</td>
            <td><form:input type="file" path="fileData"/></td>
            <td></td>
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
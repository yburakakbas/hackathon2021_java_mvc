<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Favorite List</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title"></div>

<div>Total Favorite Count: ${paginationResult.totalRecords}</div>

<table border="1" style="width:100%">
    <tr>
        <th>Favorite Id</th>
        <th>Product Code</th>
        <th>Product Name</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${paginationResult.list}" var="favorite">
        <tr>
            <td>${favorite.id}</td>
            <td>${favorite.userName}</td>
            <td>${favorite.productCode}</td>
            <td>${favorite.productPrice}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${paginationResult.totalPages > 1}">
    <div class="page-navigator">
        <c:forEach items="${paginationResult.navigationPages}" var = "page">
            <c:if test="${page != -1 }">
                <a href="orderList?page=${page}" class="nav-item">${page}</a>
            </c:if>
            <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
            </c:if>
        </c:forEach>

    </div>
</c:if>




<jsp:include page="_footer.jsp" />

</body>
</html>
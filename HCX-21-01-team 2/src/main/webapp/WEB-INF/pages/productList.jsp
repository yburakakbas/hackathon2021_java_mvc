<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://kit.fontawesome.com/295ffebcdb.js" crossorigin="anonymous"></script>
<script
        src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">--%>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-4.0.0-dist/css/bootstrap.min.css">
</head>
<script>
    $(document).ready(function () {
        <c:if test="${not empty productValidation}">
            $('#infoModalForProduct').modal({backdrop: 'static', keyboard: false});
            setTimeout(function() {$('#infoModalForProduct').modal('hide');}, 2250);
        </c:if>
        <c:if test="${not empty validationFavorite}">
            $('#infoModalForFavorite').modal({backdrop: 'static', keyboard: false});
            setTimeout(function() {$('#infoModalForFavorite').modal('hide');}, 2250);
        </c:if>
    });
</script>
<body class="col-md-12">

    <div class="modal fade custom-in" id="infoModalForProduct" tabindex="-1"
     role="dialog"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg custom-modal-dialog" role="document">
        <div class="modal-content custom-modal-content">
            <div class="modal-header custom-modal-header">
                <div class="row">
                    <div class="col-md-11">
                        <h5 class="modal-title" id="modalLabel">
                            INFO
                        </h5>
                    </div>
                </div>
                </br>
                <hr>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12" style="text-align: center;font-size: large">
                        <c:if test="${productValidation}">
                            <i class="fa fa-check-circle fa-lg fa-fw" style="color: green"></i>
                            Product successfully deleted.
                        </c:if>
                        <c:if test="${!productValidation}">
                            <i class="fa fa-times-circle fa-lg fa-fw" style="color: red"></i>
                            Product couldn't deleted. Because product valid in a order.
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <div class="modal fade custom-in" id="infoModalForFavorite" tabindex="-1"
         role="dialog"
         aria-labelledby="exampleModalLabel1" aria-hidden="true">
        <div class="modal-dialog modal-lg custom-modal-dialog" role="document">
            <div class="modal-content custom-modal-content">
                <div class="modal-header custom-modal-header">
                    <div class="row">
                        <div class="col-md-11">
                            <h5 class="modal-title" id="modalLabel1">
                                INFO
                            </h5>
                        </div>
                    </div>
                    </br>
                    <hr>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12" style="text-align: center;font-size: large">
                            <c:if test="${validationFavorite}">
                                <i class="fa fa-check-circle fa-lg fa-fw" style="color: green"></i>
                                This product successfully added to your favorites.
                            </c:if>
                            <c:if test="${!validationFavorite}">
                                <i class="fa fa-times-circle fa-lg fa-fw" style="color: red"></i>
                                This product is already in your favorites.
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">Product List</div>

<div class="account-container">
    <form action="${pageContext.request.contextPath}/productList/?" method="GET">
        Search By Name: <input type="text" name="name">
        <input type="submit" value="Search"></form>
</div>
<div class="account-container">
    <form action="${pageContext.request.contextPath}/productList/?" method="GET">
        Search By Code: <input type="text" name="code">
        <input type="submit" value="Search"></form>
</div>

<div class="menu-container">
    <a href="${pageContext.request.contextPath}/productList/" class="nav-item">All</a>
    <c:forEach var="category" items="${categoryList}">
        <a href="${pageContext.request.contextPath}/productList/?category=${category.id}"
           class="nav-item">${category.description}</a>
        <%--<c:forEach var="subcategory" items="${category.subcategory}">
            <a href="${pageContext.request.contextPath}/productList/?subcategory=${subcategory.id}"
               class="nav-item">${subcategory.description}</a>
        </c:forEach>--%>
    </c:forEach>
</div>

<div class="menu-container">
    <c:forEach var="subcategory" items="${subcategoryList}">
        <a href="${pageContext.request.contextPath}/productList/?subcategory=${subcategory.id}"
           class="nav-item">${subcategory.description}</a>
    </c:forEach>
</div>

<c:forEach items="${paginationProducts.list}" var="prodInfo">
    <div class="product-preview-container">
        <ul>
            <li><img class="product-image"
                     src="${pageContext.request.contextPath}/productImage?code=${prodInfo.code}"/></li>
            <li>Code: ${prodInfo.code}</li>
            <li>Name: ${prodInfo.name}</li>
            <li>Category: ${prodInfo.category.description}</li>
            <li>Subcategory: ${prodInfo.subcategory.description}</li>
            <li><a href="${pageContext.request.contextPath}/productDetails?id=${prodInfo.productDetails.id}">Product
                Details</a>
            </li>
            <li>Price: <fmt:formatNumber value="${prodInfo.price}" type="currency"/></li>
            <li><a
                    href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}">
                Buy Now</a></li>
            <!-- For Manager edit Product -->
            <security:authorize access="hasRole('ROLE_MANAGER')">
                <li>
                    <a style="color:red;" href="${pageContext.request.contextPath}/product?code=${prodInfo.code}">Edit
                        Product</a>
                </li>

                <li>
                    <a data-toggle="modal"
                       data-target="#deleteModal-${prodInfo.code}" href="#">
                        <i class="fas fa-trash-alt text-danger" title="Delete Product" style="font-size: 16px;"></i>
                    </a>
                    <!-- Delete Modal -->
                    <div class="modal fade custom-in" id="deleteModal-${prodInfo.code}" style="margin: auto; width: 30%"
                         tabindex="-1"
                         role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg custom-modal-dialog"
                             role="document">
                            <div class="modal-content custom-modal-content">
                                <div class="modal-header custom-modal-header">
                                    <div class="row" style="width: 100%">
                                        <div class="col-md-11">
                                            <h5 class="modal-title"
                                                id="exampleModalLabel">DELETE PRODUCT</h5>

                                        </div>

                                        <div class="col-md-1" style="float: right">
                                            <button type="button" class="close"
                                                    data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                    </div>

                                    </br>
                                    <hr>
                                </div>

                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            Are you sure want to delete this product ?
                                        </div>
                                    </div>
                                </div>

                                   <div class="modal-footer">
                                       <a
                                          href="${pageContext.request.contextPath}/productDelete?code=${prodInfo.code}"
                                          class="btn btn-danger btn-secondary"
                                          style="width: 200px">Delete</a>
                                   </div>
                               </div>
                           </div>
                       </div>
                   </li>
               </security:authorize>
            <security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_EMPLOYEE')">
                <li>
                    <form:form method="POST" modelAttribute="favoriteDTO"
                               action="${pageContext.request.contextPath}/favorite">
                        <input type="hidden" name="userName" value="${pageContext.request.userPrincipal.name}">
                        <input type="hidden" name="productCode" value="${prodInfo.code}">
                        <%--                            <form:input path="userName">${pageContext.request.userPrincipal.name}</form:input>--%>
                        <%--                            <form:input path="productCode">${prodInfo.code}</form:input>--%>
                        <button type="submit" id="addFavorite"
                                class="btn btn-block btn-primary"
                                style="border: 1.5px solid;">Add Favorite <i class="fas fa-plus-circle" aria-hidden="true" style="font-size: 16px;"></i></button>
                    </form:form>
                </li>
            </security:authorize>

           </ul>
       </div>
   </c:forEach>
   <br/>


<c:if test="${paginationProducts.totalPages > 1}">
    <div class="page-navigator">
        <c:forEach items="${paginationProducts.navigationPages}" var="page">
            <c:if test="${page != -1 }">
                <a href="productList?page=${page}" class="nav-item">${page}</a>
            </c:if>
            <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
            </c:if>
        </c:forEach>
    </div>
</c:if>

<jsp:include page="_footer.jsp"/>

</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Access Denied</title>

   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
   <link rel="stylesheet" type="text/css"
         href="${pageContext.request.contextPath}/bootstrap-4.0.0-dist/css/bootstrap.min.css">

</head>
<body class="col-md-12">


   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />

   <div class="page-title">Access Denied!</div>

   <h3 style="color:red;">Sorry, you can not access this page!</h3>


   <jsp:include page="_footer.jsp" />

</body>
</html>
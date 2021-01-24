<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title><tiles:getAsString name="title" /></title>
<link rel="icon" href="<c:url value="/public/img/app/app-icon.png"/>" />

<!-- Third-party libraries -->
<link rel="stylesheet"
	href='<c:url value="/public/vendors/animate.min.css"/>'>
<link rel="stylesheet"
	href='<c:url value="/public/vendors/fontawesome-5.15.1/css/all.min.css"/>'>
<link rel="stylesheet"
	href='<c:url value="/public/vendors/fancybox/dist/jquery.fancybox.min.css"/>'>
<link rel="stylesheet"
	href='<c:url value="/public/vendors/bootstrap-4/css/bootstrap.min.css"/>'>

<!-- App css -->
<link rel="stylesheet" href='<c:url value="/public/css/app.css"/>'>
</head>
<body>

	<!-- Header -->
	<tiles:insertAttribute name="header" />
	<!-- Header -->

	<!-- Main -->
	<tiles:insertAttribute name="body" />
	<!-- Main -->

	<!-- Main -->
	<tiles:insertAttribute name="footer" />
	<!-- Main -->

	<!-- Third-party libraries -->
	<script src='<c:url value="/public/vendors/wow.min.js"/>'></script>
	<script src='<c:url value="/public/vendors/jquery-3.5.1.min.js"/>'></script>
	<script src='<c:url value="/public/vendors/isotope.pkgd.min.js"/>'></script>
	<script src='<c:url value="/public/vendors/gsap.min.js"/>'></script>
	<script
		src='<c:url value="/public/vendors/fontawesome-5.15.1/js/all.min.js"/>'></script>
	<script
		src='<c:url value="/public/vendors/fancybox/dist/jquery.fancybox.min.js"/>'></script>
	<script
		src='<c:url value="/public/vendors/bootstrap-4/js/bootstrap.min.js"/>'></script>

	<!-- App js -->
	<script src='<c:url value="/public/js/app.js"/>'></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Header -->
<header>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

		<!-- App title -->
		<a class="navbar-brand" href="<c:url value="/"/>">
			<img src="<c:url value="/public/img/app/app-icon.png"/>"
				class="app-icon" alt="App icon">
			gBean
		</a>
		<!-- App title -->

		<!-- Toggle button -->
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<!-- Toggle button -->

		<!-- Navbar items -->
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">

			<!-- Left items -->
			<div class="navbar-nav mr-auto">
				<a class="nav-item nav-link active"
					href="<c:url value="/product/list"/>">Sản phẩm</a>
				<a class="nav-item nav-link active"
					href="<c:url value="/category/list"/>">Danh mục</a>
			</div>
			<!-- Left items -->

			<!-- Right items -->
			<div class="navbar-nav ml-auto">

				<c:choose>
					<c:when test="${ user != null }">
						<div class="nav-item dropdown active mr-5 pr-5">

							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
								role="button" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false"> ${ user.email } </a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item" href="<c:url value='/users/me'/>">
									Tài khoản <i class="fas fa-user"></i>
								</a>
								<div class="dropdown-divider"></div>

								<a class="dropdown-item"
									href="<c:url value='/product/me/list'/>">
									Sản phẩm <i class="fas fa-cubes"></i>
								</a>
								<div class="dropdown-divider"></div>

								<a class="dropdown-item"
									href="<c:url value='/auth?command=logout'/>">Đăng xuất</a>
							</div>
						</div>
					</c:when>

					<c:otherwise>
						<a class="nav-item nav-link active" href="<c:url value="/auth"/>">Đăng
							nhập</a>
						<a class="nav-item nav-link active"
							href="<c:url value="/auth?command=signup"/>">Đăng ký</a>
					</c:otherwise>
				</c:choose>

			</div>
			<!-- Right items -->

		</div>
		<!-- Navbar items -->

	</nav>
	<!-- Navbar -->

</header>
<!-- Header -->

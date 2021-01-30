<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<div
		class="d-flex justify-content-center align-items-center bg-primary" style="height: 100vh;">
		<div class="d-flex w-75 h-75 bg-light">
			<div
				class="text-white flex-grow-1 d-flex flex-column justify-content-between p-3"
				style="background: url('<c:url value="/public/img/app/background_login.png"/>') no-repeat; background-size: cover;">
				<div class="text-center">
					<h3>Luôn đồng hành cùng bạn</h3>
				</div>
				<div>
					<p>
						<img alt="icon_oval"
							src='<c:url value="/public/img/app/icon_oval.png"/>' />
						Luôn cập nhật xu hướng thời trang mới nhất
					</p>
					<p>
						<img alt="icon_oval"
							src="<c:url value="/public/img/app/icon_oval.png"/>" />
						Giảm hơn 50% đối với khách VIP
					</p>
					<p>
						<img alt="icon_oval"
							src="<c:url value="/public/img/app/icon_oval.png" />">
						Tận tình tư vấn để tạo nên phong cách thời trang của bạn
					</p>
				</div>
			</div>

			<div
				class="p-3 flex-grow-1 d-flex flex-column justify-content-between">
				<div class="text-primary">
					<ol class="breadcrumb bg-light">
						<li class="breadcrumb-item">
							<a id="link-login"
								class="text-decoration-none font-weight-bold text-uppercase pointer-cursor"
								href="#">Đăng nhập</a>
						</li>
						<li class="breadcrumb-item">
							<a id="link-signup"
								class="text-decoration-none font-weight-bold text-uppercase pointer-cursor"
								href="#">Đăng ký</a>
						</li>
						<li class="breadcrumb-item">
							<a class="text-decoration-none font-weight-bold text-uppercase"
								href="<c:url value="/" />">Quay về trang chủ</a>
						</li>
					</ol>
				</div>

				<div>
					<!-- Login form -->
					<form id="form-login">
						<div class="form-group">
							<input type="email" class="form-control"
								placeholder="Enter email" name="email" required>
						</div>
						<div class="form-group">
							<input type="password" class="form-control"
								placeholder="Enter password" name="password" required>
						</div>
						<button id="btn-login" type="button" class="btn btn-primary">Đăng
							nhập</button>
					</form>
					<!-- Login form -->

					<!-- Sign up form -->
					<form id="form-signup">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Enter name"
								name="name" required>
						</div>
						<div class="form-group">
							<input type="email" class="form-control"
								placeholder="Enter email" name="email" required>
						</div>
						<div class="form-group">
							<input type="password" class="form-control"
								placeholder="Enter password" name="password" required>
						</div>
						<button id="btn-signup" type="button" class="btn btn-primary">Đăng
							ký</button>
					</form>
					<!-- Sign up form -->

				</div>

				<div>
					<div class="container-login__social text-right">
						<img alt="icon_oval"
							src="<c:url value="/public/img/app/icon_facebook.png"/>">
						<img alt="icon_oval"
							src="<c:url value="/public/img/app/icon_google.png"/>">
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="hidden-command" value="${ command }">
</main>

<script>
	document.addEventListener("DOMContentLoaded", function() {
		const hiddenCommand = $("#hidden-command").val();

		const linkLogin = $("#link-login");
		const linkSignup = $("#link-signup");
		const btnLogin = $("#btn-login");
		const btnSignup = $("#btn-signup");
		const formLogin = $("#form-login");
		const formSignup = $("#form-signup");

		function forLoginMode() {
			formLogin.show();
			formSignup.hide();
			linkLogin.addClass("text-success");
			linkSignup.removeClass("text-success");
		}

		function forSignupMode() {
			formLogin.hide();
			formSignup.show();
			linkLogin.removeClass("text-success");
			linkSignup.addClass("text-success");
		}

		if (hiddenCommand === "login") {
			forLoginMode();
		} else if (hiddenCommand === "signup") {
			forSignupMode();
		}

		linkLogin.click(function(e) {
			e.preventDefault();
			forLoginMode();
		});

		linkSignup.click(function(e) {
			e.preventDefault();
			forSignupMode();
		});

		btnLogin.click(function(e) {
			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type : "POST",
				url : "/" + rootContextPath + "/auth/ajax-login",
				data : {
					email : formLogin.find("[name='email']").val(),
					password : formLogin.find("[name='password']").val(),
				},
				success : function(redirectLink) {
					if (redirectLink) {
						// redirect to "/"
						window.location.href = window.location.pathname.substr(0, window.location.pathname.lastIndexOf('/')) + redirectLink;
					} else {
						alert("Cannot login account. Please try again!");
					}
				}
			});
		});

		btnSignup.click(function(e) {
			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type : "POST",
				url : "/" + rootContextPath + "/auth/ajax-signup",
				data : {
					name : formSignup.find("[name='name']").val(),
					email : formSignup.find("[name='email']").val(),
					password : formSignup.find("[name='password']").val(),
				},
				success : function(errorReasons) {
					if (errorReasons) {
						alert("Cannot sign up account. Please try again!\nReason(s):\n" + errorReasons);
					} else {
						alert("Sign up account successfully.");
						forLoginMode();
					}
				}
			});
		});
	});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<div class="container-fluid">
		<div class="container__intro row text-center my-3">
			<div class="col-4">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">
							<i class="fas fa-money-check-alt"></i>
						</h4>
						<p class="card-text">Tiết kiệm chi phí</p>
					</div>
				</div>
			</div>

			<div class="col-4">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">
							<i class="fas fa-medal"></i>
						</h4>
						<p class="card-text">Chất lượng đảm bảo</p>
					</div>
				</div>
			</div>

			<div class="col-4">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">
							<i class="fas fa-truck"></i>
						</h4>
						<p class="card-text">Vận chuyển tận nơi</p>
					</div>
				</div>
			</div>
		</div>

		<div class="container__products row my-3">

			<c:forEach var="product" items="${ products }">
				<div class="col-sm-6 col-md-3">
					<div class="card my-3">
						<img class="card-img-top"
							src="<c:url value="/public/img/product-upload/${ product.img }"/>"
							alt="Card image cap" />
						<div class="card-body">
							<h4 class="card-title">${ product.name }</h4>
							<p class="card-text">${ product.cost }$</p>
							<p class="card-text">${ product.description }</p>
							<a class="btn btn-primary"
								href="<c:url value="/product/detail/${ product.id }"/>">Chi
								tiết</a>
						</div>
					</div>
				</div>
			</c:forEach>

		</div>

		<nav class="my-3" aria-label="Page navigation">
			<ul class="pagination justify-content-end">

				<c:forEach var="i" begin="1" end="${ page.totalPages }" step="1">
					<c:choose>
						<c:when test="${ i == 1 }">
							<li class="page-item active">
								<a class="page-link" href="#!">${ i }</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="page-item">
								<a class="page-link" href="#!">${ i }</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

			</ul>
		</nav>
	</div>
</main>

<script>
	document.addEventListener("DOMContentLoaded", function() {
		$("body").on("click", ".page-item", function() {
			const self = $(this);
			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type : "GET",
				url : "/" + rootContextPath + "/product/ajax-paging-list",
				data : {
					pageNumber: $(this).text(),
				},
				success : function({page, products}) {
					let html = ``;
					products.forEach(product => {   
						html += "<div class='col-sm-6 col-md-3'> <div class='card my-3'> <img class='card-img-top' src='/" + rootContextPath + "/public/img/product-upload/" + product.img + "' alt='Card image cap' /> <div class='card-body'> <h4 class='card-title'>" + product.name + "</h4> <p class='card-text'>" + product.cost + "$</p> <p class='card-text'>" + product.description + "</p> <a class='btn btn-primary' href='/" + rootContextPath + "/product/detail/" + product.id + "'>Chi tiết</a> </div> </div> </div>";
					});

					$(".container__products").html(html);
					$(".page-item").removeClass("active");
					self.addClass("active");
				}
			});
		});
	});
</script>

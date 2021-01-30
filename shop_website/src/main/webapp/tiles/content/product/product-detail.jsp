<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<div class="container-fluid">
		<div class="row my-3">
			<div class="col-sm-12 col-md-3">
				<img class="w-100"
					src="<c:url
          value="/public/img/product-upload/${ product.img }"
        />"
					alt="Product img" />
			</div>
			<div class="col-sm-9 col-md-6">
				<h3 data-product-id="${ product.id }">
					<strong data-product-name="${ product.name }">${ product.name }</strong>
					<strong data-product-cost="${ product.cost }">${ product.cost }$</strong>
				</h3>

				<table class="table text-center">
					<thead class="thead-dark">
						<tr>
							<th scope="col">#</th>
							<th scope="col">Quantity</th>
							<th scope="col">Color</th>
							<th scope="col">Size</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${ product.productDetails.size() > 0 }">
								<c:forEach var="productDetail"
									items="${ product.productDetails }">
									<tr>
										<th scope="row" data-product-detail-id="${ productDetail.id }">${ productDetail.id }</th>
										<td data-product-detail-qty="${ productDetail.qty }">${ productDetail.qty }</td>
										<td data-color-id="${ productDetail.color.id }"
											data-color-name="${ productDetail.color.name }">${ productDetail.color.name }</td>
										<td data-size-id="${ productDetail.size.id }"
											data-size-name="${ productDetail.size.name }">${ productDetail.size.name }</td>
										<td>
											<button class="btn btn-primary btn-add-to-cart">Add
												to cart</button>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5">
										<strong>No product details</strong>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="col-sm-3 col-md-3">${ product.description }</div>
		</div>
	</div>
</main>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    $(".btn-add-to-cart").click(function (e) {
      const tableRow = $(this).closest("tr");

      const productId = $("[data-product-id]").data("product-id");
      const productName = $("[data-product-name]").data("product-name");
      const productCost = $("[data-product-cost]").data("product-cost");
      const productDetailId = tableRow
        .children("[data-product-detail-id]")
        .data("product-detail-id");
      const productDetailQty = tableRow
        .children("[data-product-detail-qty]")
        .data("product-detail-qty");
      const colorId = tableRow.children("[data-color-id]").data("color-id");
      const colorName = tableRow
        .children("[data-color-name]")
        .data("color-name");
      const sizeId = tableRow.children("[data-size-id]").data("size-id");
      const sizeName = tableRow.children("[data-size-name]").data("size-name");

      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      $.ajax({
        type: "POST",
        url: "/" + rootContextPath + "/product/ajax-add-to-cart",
        data: {
          cartItemJSON: JSON.stringify({
            productId,
            productName,
            productCost,
            productDetailId,
            productDetailQty,
            colorId,
            colorName,
            sizeId,
            sizeName,
          }),
        },
        success: function (cartSize) {
          if (cartSize > 0) {
            $("#cart-size").text(cartSize);
          }
        },
      });
    });
  });
</script>

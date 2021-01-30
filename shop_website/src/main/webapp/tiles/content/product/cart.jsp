<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<div class="container-fluid">
		<h2>Your shopping cart</h2>
		<div class="row">
			<div class="col-md-8">
				<h3>Danh sách sản phầm trong giỏ hàng</h3>
				<table class="table text-center">
					<thead class="thead-dark">
						<tr>
							<th>#</th>
							<th>Product Name</th>
							<th>Product Cost</th>
							<th>Quantity</th>
							<th>Color</th>
							<th>Size</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${ cart.size() > 0 }">
								<c:forEach var="cartItem" items="${ cart }">
									<tr>
										<th scope="row" data-product-id="${ cartItem.productId }">${ cartItem.productId }</th>
										<td data-product-name="${ cartItem.productName }">${ cartItem.productName }</td>
										<td data-product-cost="${ cartItem.productCost }">${ cartItem.productCost }$</td>
										<td>
											<input type="number" min="1"
												data-product-detail-id="${ cartItem.productDetailId }"
												data-product-detail-qty="${ cartItem.productDetailQty }"
												value="${ cartItem.productDetailQty }">
										</td>
										<td data-color-id="${ cartItem.colorId }"
											data-color-name="${ cartItem.colorName }">${ cartItem.colorName }</td>
										<td data-size-id="${ cartItem.sizeId }"
											data-size-name="${ cartItem.sizeName }">${ cartItem.sizeName }</td>
										<td>
											<button class="btn btn-secondary btn-remove-from-cart">Remove
												from cart</button>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="7">
										<strong>Your cart is empty</strong>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<strong id="sum-cost">0$</strong>
			</div>

			<div class="col-md-4">
				<h3>Thông tin order</h3>
				<form>
					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter your name" name="userName" required>
					</div>
					<div class="form-group">
						<input type="number" class="form-control"
							placeholder="Enter your phone number" name="userPhoneNumber" required>
					</div>
					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter your address" name="userAddress" required>
					</div>
					<button type="button" id="btn-order" class="btn btn-primary">Order</button>
				</form>
			</div>
		</div>
	</div>
</main>

<script>
	document.addEventListener("DOMContentLoaded",function() {
		function sumCost() {
			let sumCost = 0;

			$("[data-product-cost]").each(function (index, element) {
				// element == this
				const tableRow = $(this).closest("tr");

				const productCost = $(this).data("product-cost");
				const productDetailQty = tableRow.children().children("[data-product-detail-qty]").val();
				
				$(this).text(productCost * productDetailQty + "$");

				sumCost += (productCost * productDetailQty);
			});

			$("#sum-cost").text(sumCost + "$");
		}

		sumCost();
		$("[data-product-detail-qty]").change(function(e) {
			const tableRow = $(this).closest("tr");

			const productId = tableRow
					.children("[data-product-id]")
					.data("product-id");
			const productCost = tableRow
					.children("[data-product-cost]")
					.data("product-cost");
			const productDetailQty = $(this).val();
			const colorId = tableRow
					.children("[data-color-id]")
					.data("color-id");
			const sizeId = tableRow
					.children("[data-size-id]")
					.data("size-id");

			// Dung hàm sumCost() nên không cần dùng đoạn này nữa, nhưng vẫn giữ nó lại để sau này xem lại code có thể hiểu dễ dàng 
			// tableRow.children("[data-product-cost]")
			// 		.text(productCost * productDetailQty + "$");

			// Update front-end
			sumCost();

			// Pass data to back-end
			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type: "POST",
				url: "/" + rootContextPath + "/product/ajax-update-cart",
				data: {
					productId,
					productDetailQty,
					colorId,
					sizeId,
				},
				success: function (isUpdateSuccess) {
					if (isUpdateSuccess) {
						alert("Update successfully.");
					}
				}
			});
		});

		
		$(".btn-remove-from-cart").click(function (e) { 
			const tableRow = $(this).closest("tr");

			const productId = tableRow
					.children("[data-product-id]")
					.data("product-id");
			const colorId = tableRow
					.children("[data-color-id]")
					.data("color-id");
			const sizeId = tableRow
					.children("[data-size-id]")
					.data("size-id");

			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type: "POST",
				url: "/" + rootContextPath + "/product/ajax-remove-from-cart",
				data: {
					productId, 
					colorId,
					sizeId,
				},
				success: function (cartSize) {
					tableRow.remove();
					sumCost();

					if (cartSize > 0) {
						$("#cart-size").text(cartSize);
					}
				}
			});
		});

		$("#btn-order").click(function (e) { 
			const userName = $("[name='userName']").val();
			const userPhoneNumber = $("[name='userPhoneNumber']").val();
			const userAddress = $("[name='userAddress']").val();

			const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

			$.ajax({
				type: "POST",
				url: "/" + rootContextPath + "/bill/ajax-order",
				data: {
					userName,
					userPhoneNumber,
					userAddress,
				},
				success: function (isOrderSuccess) {
					if (isOrderSuccess) {
						alert("Order successfully.");
					} else {
						alert("Order fail. Please try again!");
						alert("Maybe your cart is empty or your information is empty. Check again!");
					}
				}
			});
		});
	});
</script>

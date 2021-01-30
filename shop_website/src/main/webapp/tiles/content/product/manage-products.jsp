<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
  <div class="container-fluid">
    <h2>Products management</h2>
    <div class="row">
      <div class="col-md-6">
        <h3>Danh sách</h3>
        <table class="table table-hover text-center">
          <thead class="thead-dark">
            <tr>
              <th>#</th>
              <th>Product Name</th>
              <th>Product Cost</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${ products.size() > 0 }">
                <c:forEach var="product" items="${ products }">
                  <tr>
                    <th scope="row" data-product-id="${ product.id }">
                      ${ product.id }
                    </th>
                    <td>${ product.name }</td>
                    <td>${ product.cost }$</td>
                  </tr>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <tr>
                  <td colspan="4">
                    <strong>No products.</strong>
                  </td>
                </tr>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>

        <nav class="my-3" aria-label="Page navigation">
          <ul class="pagination">
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
      <div class="col-md-6">
        <form>
          <h3>Thông tin sản phẩm</h3>

          <div class="form-group">
            <input
              type="text"
              class="form-control"
              placeholder="Enter product name"
              name="productName"
              required
            />
          </div>

          <div class="form-group">
            <input
              type="number"
              class="form-control"
              placeholder="Enter product cost"
              name="productCost"
              required
            />
          </div>

          <div class="form-group">
            <label>Product Description</label>
            <textarea
              class="form-control"
              name="productDescription"
              rows="3"
            ></textarea>
          </div>

          <div class="form-group">
            <label>Product image</label>
            <input type="file" name="productImg" class="form-control-file" />
          </div>

          <div class="form-group">
            <label>Category</label>
            <select class="form-control" name="categoryId">
              <c:forEach var="category" items="${ categories }">
                <option value="${ category.id }">${ category.name }</option>
              </c:forEach>
            </select>
          </div>
        </form>

        <div id="content-container"></div>

        <button
          type="button"
          id="btn-new-product-detail"
          class="btn btn-success btn-sm"
          style="margin: 5px 0 10px auto; display: block"
        >
          New product detail
          <i class="fa fa-plus-circle" aria-hidden="true"></i>
        </button>

        <div id="sample-container" class="product-detail-info d-none">
          <hr class="my-4" />
          <hr class="my-4" />

          <h3>Thông tin chi tiết sản phẩm</h3>

          <input type="hidden" name="productDetailId" />

          <div class="form-group">
            <label>Product detail quantity</label>
            <input
              class="form-control"
              type="number"
              name="productDetailQty"
              placeholder="Enter product quantity"
              min="1"
              value="1"
            />
          </div>

          <div class="form-group">
            <label>Color</label>
            <select class="form-control" name="colorId">
              <c:forEach var="color" items="${ colors }">
                <option value="${ color.id }">${ color.name }</option>
              </c:forEach>
            </select>
          </div>

          <div class="form-group">
            <label>Size</label>
            <select class="form-control" name="sizeId">
              <c:forEach var="size" items="${ sizes }">
                <option value="${ size.id }">${ size.name }</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div class="form-group">
          <button type="button" id="btn-cancel" class="btn btn-warning d-none">
            Cancel
          </button>
          <button
            type="button"
            id="btn-add-product"
            class="btn btn-success d-none"
          >
            Add product
          </button>
          <button
            type="button"
            id="btn-update-product"
            class="btn btn-primary d-none"
          >
            Update product
          </button>
          <button
            type="button"
            id="btn-remove-product"
            class="btn btn-secondary d-none"
          >
            Remove product
          </button>
        </div>
      </div>
    </div>
  </div>
</main>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    $("body").on("click", ".page-item", function () {
      const self = $(this);
      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      $.ajax({
        type: "GET",
        url: "/" + rootContextPath + "/product/ajax-paging-list",
        data: {
          pageNumber: $(this).text(),
        },
        success: function ({ page, products }) {
          let html = ``;
          products.forEach((product) => {
            html +=
              "<tr> <th scope='row' data-product-id='" +
              product.id +
              "'>" +
              product.id +
              "</th> <td>" +
              product.name +
              "</td> <td>" +
              product.cost +
              "$</td> </tr>";
          });

          $("tbody").html(html);
          $(".page-item").removeClass("active");
          self.addClass("active");
        },
      });
    });

    const btnNewProductDetail = $("#btn-new-product-detail");
    const btnCancel = $("#btn-cancel");
    const btnAddProduct = $("#btn-add-product");
    const btnUpdateProduct = $("#btn-update-product");
    const btnRemoveProduct = $("#btn-remove-product");

    const contentContainer = $("#content-container");
    const sampleContainer = $("#sample-container");

    btnNewProductDetail.click(function (e) {
      $(this).removeClass("d-none");
      btnCancel.removeClass("d-none");
      btnAddProduct.removeClass("d-none");
      btnUpdateProduct.addClass("d-none");
      btnRemoveProduct.addClass("d-none");

      const content = sampleContainer
        .clone()
        .removeAttr("id")
        .removeClass("d-none");
      contentContainer.append(content);
    });

    btnCancel.click(function (e) {
      btnNewProductDetail.removeClass("d-none");
      $(this).addClass("d-none");
      btnAddProduct.addClass("d-none");
      btnUpdateProduct.addClass("d-none");
      btnRemoveProduct.addClass("d-none");

      contentContainer.html("");
    });

    btnAddProduct.click(function (e) {
      const formData = new FormData($("form")[0]);

      let dataJson = {};
      for (var key of formData.keys()) {
        if (key === "productImg") {
          dataJson[key] = formData.get(key).name;
        } else {
          dataJson[key] = formData.get(key);
        }
      }

      dataJson.productDetails = [];
      $("#content-container .product-detail-info").each(function (
        index,
        element
      ) {
        // element == this
        let tmp = {};
        tmp.productDetailQty = $(this).find("[name='productDetailQty']").val();
        tmp.colorId = $(this).find("[name='colorId']").val();
        tmp.sizeId = $(this).find("[name='sizeId']").val();

        dataJson.productDetails.push(tmp);
      });

      const reqFormData = new FormData();
      reqFormData.append("dataJson", JSON.stringify(dataJson));
      reqFormData.append("productImg", formData.get("productImg"));

      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      $.ajax({
        type: "POST",
        url: "/" + rootContextPath + "/product/ajax-add-product",
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        data: reqFormData,
        success: function (isAddSuccess) {
          if (isAddSuccess) {
            alert("Add product successfully.");
          } else {
            alert("Add product fail. Please try again!");
          }
        },
      });
    });

    let productId = 0;
    $("body").on("click", "tr", function () {
      btnNewProductDetail.addClass("d-none");
      btnCancel.removeClass("d-none");
      btnAddProduct.addClass("d-none");
      btnUpdateProduct.removeClass("d-none");
      btnRemoveProduct.removeClass("d-none");

      productId = $(this).children("[data-product-id]").data("product-id");

      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      $.ajax({
        type: "POST",
        url: "/" + rootContextPath + "/product/ajax-pre-fill",
        data: {
          productId,
        },
        success: function (productDTO) {
          if (productDTO) {
            $("[name='productName']").val(productDTO.name);
            $("[name='productCost']").val(productDTO.cost);
            $("[name='productDescription']").val(productDTO.description);
            $("[name='categoryId']").val(productDTO.categoryDTO.id);

            contentContainer.empty();
            if (productDTO.productDetailDTOs.length > 0) {
              productDTO.productDetailDTOs.forEach((productDetailDTO) => {
                const content = sampleContainer
                  .clone()
                  .removeAttr("id")
                  .removeClass("d-none");
                content
                  .find("[name='productDetailId']")
                  .val(productDetailDTO.id);
                content
                  .find("[name='productDetailQty']")
                  .val(productDetailDTO.qty);
                content
                  .find("[name='colorId']")
                  .val(productDetailDTO.colorDTO.id);
                content
                  .find("[name='sizeId']")
                  .val(productDetailDTO.sizeDTO.id);
                contentContainer.append(content);
              });
            }
          }
        },
      });
    });

    btnUpdateProduct.click(function (e) {
      const formData = new FormData($("form")[0]);

      let dataJson = {};
      for (var key of formData.keys()) {
        if (key === "productImg") {
          dataJson[key] = formData.get(key).name;
        } else {
          dataJson[key] = formData.get(key);
        }
      }

      dataJson.productDetails = [];
      $("#content-container .product-detail-info").each(function (
        index,
        element
      ) {
        // element == this
        let tmp = {};
        tmp.productDetailId = $(this).find("[name='productDetailId']").val();
        tmp.productDetailQty = $(this).find("[name='productDetailQty']").val();
        tmp.colorId = $(this).find("[name='colorId']").val();
        tmp.sizeId = $(this).find("[name='sizeId']").val();

        dataJson.productDetails.push(tmp);
      });

      if (productId > 0) {
        dataJson.productId = productId;
      }

      const reqFormData = new FormData();
      reqFormData.append("dataJson", JSON.stringify(dataJson));
      reqFormData.append("productImg", formData.get("productImg"));

      console.log(dataJson);

      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      $.ajax({
        type: "POST",
        url: "/" + rootContextPath + "/product/ajax-update-product",
        enctype: "multipart/form-data",
        processData: false,
        contentType: false,
        data: reqFormData,
        success: function (isUpdateSuccess) {
          if (isUpdateSuccess) {
            alert("Update product successfully.");
          } else {
            alert("Update product fail. Please try again!");
          }
        },
      });

      // reset global scope productId for next req
      productId = 0;
    });

    btnRemoveProduct.click(function (e) {
      btnCancel.trigger("click");

      const rootContextPath = window.location.pathname.match(/[^/]+/)[0];

      if (productId > 0) {
        $.ajax({
          type: "POST",
          url: "/" + rootContextPath + "/product/ajax-remove-product",
          data: {
            productId,
          },
          success: function (isDeleteSuccess) {
            if (isDeleteSuccess) {
              window.location.reload(false);
              alert("Delete product successfully.");
            } else {
              alert("Delete product fail. Please try again!");
            }
          },
        });
      }
    });
  });
</script>

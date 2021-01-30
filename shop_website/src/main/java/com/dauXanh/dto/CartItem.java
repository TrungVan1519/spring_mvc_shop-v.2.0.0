package com.dauXanh.dto;

public class CartItem {

	int productId;
	String productName;
	int productCost;

	int productDetailId;
	int productDetailQty;

	int colorId;
	String colorName;

	int sizeId;
	String sizeName;

	public CartItem(int productId, String productName, int productCost, int productDetailId, int productDetailQty,
			int colorId, String colorName, int sizeId, String sizeName) {
		this.productId = productId;
		this.productName = productName;
		this.productCost = productCost;
		this.productDetailId = productDetailId;
		this.productDetailQty = productDetailQty;
		this.colorId = colorId;
		this.colorName = colorName;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCost() {
		return productCost;
	}

	public void setProductCost(int productCost) {
		this.productCost = productCost;
	}

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public int getProductDetailQty() {
		return productDetailQty;
	}

	public void setProductDetailQty(int productDetailQty) {
		this.productDetailQty = productDetailQty;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
}
